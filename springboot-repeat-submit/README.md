# 工程简介
> 处理重复请求接口，防止重复添加数据（核心）


## 一、项目搭建过程
### 1.maven依赖导入 和 配置文件
#### （1.1）maven依赖导入
```xml
<dependencies>
<dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
        <exclusions>
            <exclusion>
                <groupId>redis.clients</groupId>
                <artifactId>jedis</artifactId>
            </exclusion>
            <exclusion>
                <groupId>io.lettuce</groupId>
                <artifactId>lettuce-core</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
    <!-- jedis -->
    <dependency>
        <groupId>redis.clients</groupId>
        <artifactId>jedis</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-aop</artifactId>
    </dependency>
</dependencies>
```
#### （1.2）配置文件
```properties
# 应用名称
spring.application.name=repeat_submit_intercept
# 应用服务 WEB 访问端口
server.port=8080
##====================redis
# Redis数据库索引（默认为0）
spring.redis.database=0
# Redis服务器地址
spring.redis.host=localhost
# Redis服务器连接端口
spring.redis.port=6379
# Redis服务器连接密码（默认为空）
#spring.redis.password=yourpwd
# 连接池最大连接数（使用负值表示没有限制）
spring.redis.jedis.pool.max-active=8
# 连接池最大阻塞等待时间
spring.redis.jedis.pool.max-wait=-1ms
# 连接池中的最大空闲连接
spring.redis.jedis.pool.max-idle=8
# 连接池中的最小空闲连接
spring.redis.jedis.pool.min-idle=0
# 连接超时时间（毫秒）
spring.redis.timeout=5000ms
```

#### (1.3) 启动类添加Bean对象
```java
@SpringBootApplication
public class RepeatSubmitInterceptApplication {
    public static void main(String[] args) {SpringApplication.run(RepeatSubmitInterceptApplication.class, args);}

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {return new RestTemplate(factory);}

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        return factory;
    }
}
```

### 2.工具类准备
#### (2.1) 统一返回类：`ApiResult.java`
```java
public class ApiResult {
    private Integer code;

    private String message;

    private Object data;

    public ApiResult(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ApiResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
```

#### （2.2）Redis锁：`RedisLock.java`
```java
@Service
public class RedisLock {

    private static final Long RELEASE_SUCCESS = 1L;
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    /**
     * 当前设置 过期时间单位, EX = seconds; PX = milliseconds
     */
    private static final String SET_WITH_EXPIRE_TIME = "EX";
    private static final String RELEASE_LOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 该加锁方法仅针对单实例 Redis 可实现分布式加锁
     * 对于 Redis 集群则无法使用
     * <p>
     * 支持重复，线程安全
     *
     * @param lockKey  加锁键
     * @param clientId 加锁客户端唯一标识(采用UUID)
     * @param seconds  锁过期时间(秒)
     * @return
     */
    public boolean tryLock(String lockKey, String clientId, int seconds) {
        return redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
            Jedis jedis = (Jedis) redisConnection.getNativeConnection();
            String result = jedis.set(lockKey, clientId, SetParams.setParams().ex(seconds));
            if (LOCK_SUCCESS.equals(result)) {
                return true;
            }
            return false;
        });
    }

    /**
     * 该加锁方法仅针对单实例 Redis 可实现分布式加锁
     * 对于 Redis 集群则无法使用
     * <p>
     * 支持重复，线程安全
     *
     * @param lockKey      加锁键
     * @param clientId     加锁客户端唯一标识(采用UUID)
     * @param milliSeconds 锁过期时间(毫秒)
     * @return
     */
    public boolean tryLock(String lockKey, String clientId, long milliSeconds) {
        return redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
            Jedis jedis = (Jedis) redisConnection.getNativeConnection();
            String result = jedis.set(lockKey, clientId, SetParams.setParams().px(milliSeconds));
            if (LOCK_SUCCESS.equals(result)) {
                return true;
            }
            return false;
        });
    }

    /**
     * 与 tryLock 相对应，用作释放锁
     *
     * @param lockKey
     * @param clientId
     * @return
     */
    public boolean releaseLock(String lockKey, String clientId) {
        return redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
            Jedis jedis = (Jedis) redisConnection.getNativeConnection();
            Object result = jedis.eval(RELEASE_LOCK_SCRIPT, Collections.singletonList(lockKey),
                    Collections.singletonList(clientId));
            if (RELEASE_SUCCESS.equals(result)) {
                return true;
            }
            return false;
        });
    }
}
```

#### (2.3) 请求工具类：`RequestUtils.java`
```java
public class RequestUtils {
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes ra= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return ra.getRequest();
    }
}
```


### 3.实体类建立
```java
public class User implements Serializable {
    private Long userId;
    private String userName;

    public User(){}

    public User(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                '}';
    }
}
```

### 4.核心注解类和切面
> 核心内容：Spring Boot 注解 + 切面
#### （4.1）注解类
> 定义注解：`@NoRepeatSubmit`
```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NoRepeatSubmit {

    /**
     * 设置请求锁定时间
     *
     * @return
     */
    int lockTime() default 10;

}
```


#### （4.2）切面类
> 添加核心切面方法处理类 `RepeatSubmitAspect.java`
```java
@Aspect
@Component
public class RepeatSubmitAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(RepeatSubmitAspect.class);

    @Autowired
    private RedisLock redisLock;

    @Pointcut("@annotation(noRepeatSubmit)")
    public void pointCut(NoRepeatSubmit noRepeatSubmit) {
    }

    @Around("pointCut(noRepeatSubmit)")
    public Object around(ProceedingJoinPoint pjp, NoRepeatSubmit noRepeatSubmit) throws Throwable {
        int lockSeconds = noRepeatSubmit.lockTime();

        HttpServletRequest request = RequestUtils.getRequest();
        Assert.notNull(request, "request can not null");

        // 此处可以用token或者JSessionId
        String token = request.getHeader("Authorization");
        String path = request.getServletPath();
        String key = getKey(token, path);
        String clientId = getClientId();

        boolean isSuccess = redisLock.tryLock(key, clientId, lockSeconds);
        LOGGER.info("tryLock key = [{}], clientId = [{}]", key, clientId);

        if (isSuccess) {
            LOGGER.info("tryLock success, key = [{}], clientId = [{}]", key, clientId);
            // 获取锁成功
            Object result;

            try {
                // 执行进程
                result = pjp.proceed();
            } finally {
                // 解锁
                redisLock.releaseLock(key, clientId);
                LOGGER.info("releaseLock success, key = [{}], clientId = [{}]", key, clientId);
            }

            return result;

        } else {
            // 获取锁失败，认为是重复提交的请求
            LOGGER.info("tryLock fail, key = [{}]", key);
            return new ApiResult(400, "重复请求，请稍后再试", null);
        }

    }

    private String getKey(String token, String path) {
        return token + path;
    }

    private String getClientId() {
        return UUID.randomUUID().toString();
    }
}
```

### 5.控制层`SubmitController.java`
```java
@RestController
@RequestMapping(path = "/noRepeat")
public class SubmitController {
    @Resource
    private RedisTemplate redisTemplate;

    @PostMapping("/submit")
    @NoRepeatSubmit(lockTime = 300)
    public Object submit(@RequestBody User userBean) {
        try {
            // 模拟业务场景
            Thread.sleep(500);
            System.out.println(userBean.toString());
            redisTemplate.opsForList().leftPush(UUID.randomUUID().toString().replaceAll("-", ""), userBean);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ApiResult(200, "成功", userBean.toString());
    }
}
```

### 6.模拟并发测试`RunTest.java`和`RunTest2.java`
> 两者是一样原理，实现思路不同。
#### (6.1) RunTest.java
```java
@Component
public class RunTest implements ApplicationRunner {
    @Resource
    private RestTemplate restTemplate;

    @Override
    public void run(ApplicationArguments args) {
        // 执行次数
        int execCount = 10;
        System.out.println("执行多线程测试 >>>>> RunTest.java");
        String url = "http://localhost:8080/noRepeat/submit";
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < execCount; i++) {
            Long userId = Long.parseLong(String.valueOf(1));
            String userName = "userName" + 1;
            User user = new User(userId, userName);
            HttpEntity request = buildRequest(user);
            executorService.submit(() -> {
                try {
                    countDownLatch.await();
                    System.out.println("Thread:" + Thread.currentThread().getName() + ", time:" + System.currentTimeMillis());
                    ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
                    System.out.println("Thread:" + Thread.currentThread().getName() + "," + response.getBody());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        countDownLatch.countDown();
    }

    private HttpEntity buildRequest(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "yourToken");
        Map<String, Object> body = new HashMap<>(2);
        body.put("userId", user.getUserId());
        body.put("userName", user.getUserName());
        return new HttpEntity<>(body, headers);
    }
}
```

#### (6.2) RunTest2.java
```java
@Component
public class RunTest2 implements ApplicationRunner {
    @Resource
    private RestTemplate restTemplate;

    static int count = 0;
    /**
     * 总访问量是clientNum
     */
    private int threadNum = 4;
    /**
     * 并发量是threadNum
     */
    private int clientNum = 10;

    float avgExecTime = 0;
    float sumExecTime = 0;
    long firstExecTime = Long.MAX_VALUE;
    long lastDoneTime = Long.MIN_VALUE;
    float totalExecTime = 0;

    class ThreadRecord {
        long startTime;
        long endTime;

        public ThreadRecord(long st, long et) {
            this.startTime = st;
            this.endTime = et;
        }
    }

    @Override
    public void run(ApplicationArguments args) {
        final ConcurrentHashMap<Integer, ThreadRecord> records = new ConcurrentHashMap<>();
        // 建立ExecutorService线程池，threadNum个线程可以同时访问
        ExecutorService exec = Executors.newFixedThreadPool(threadNum);
        // 模拟clientNum个客户端访问
        final CountDownLatch doneSignal = new CountDownLatch(clientNum);

        System.out.println("执行多线程测试>>>>>RunTest2.java");
        String url = "http://localhost:8080/noRepeat/submit";

        for (int i = 0; i < clientNum; i++) {
            Long userId = Long.parseLong(String.valueOf(1));
            String userName = "userName" + 1;
            User user = new User(userId, userName);
            HttpEntity request = buildRequest(user);

            Runnable run = () -> {
                int index = getIndex();
                long systemCurrentTimeMillis = System.currentTimeMillis();
                try {
                    System.out.println("Thread:" + Thread.currentThread().getName() + ", time:" + System.currentTimeMillis());
                    ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
                    System.out.println("Thread:" + Thread.currentThread().getName() + "," + response.getBody());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                records.put(index, new ThreadRecord(systemCurrentTimeMillis, System.currentTimeMillis()));
                doneSignal.countDown();// 每调用一次countDown()方法，计数器减1
            };
            exec.execute(run);
        }

        try {
            // 计数器大于0 时，await()方法会阻塞程序继续执行
            doneSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**
         * 获取每个线程的开始时间和结束时间
         */
        for (int i : records.keySet()) {
            ThreadRecord r = records.get(i);
            sumExecTime += ((double) (r.endTime - r.startTime)) / 1000;

            if (r.startTime < firstExecTime) {
                firstExecTime = r.startTime;
            }
            if (r.endTime > lastDoneTime) {
                this.lastDoneTime = r.endTime;
            }
        }

        this.avgExecTime = this.sumExecTime / records.size();
        this.totalExecTime = ((float) (this.lastDoneTime - this.firstExecTime)) / 1000;
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(4);

        System.out.println("======================================================");
        System.out.println("线程数量:\t\t" + threadNum);
        System.out.println("客户端数量:\t" + clientNum);
        System.out.println("平均执行时间:\t" + nf.format(this.avgExecTime) + "秒");
        System.out.println("总执行时间:\t" + nf.format(this.totalExecTime) + "秒");
        System.out.println("吞吐量:\t\t" + nf.format(this.clientNum / this.totalExecTime) + "次每秒");
    }

    public static int getIndex() {
        return ++count;
    }

    private HttpEntity buildRequest(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "yourToken");
        Map<String, Object> body = new HashMap<>(2);
        body.put("userId", user.getUserId());
        body.put("userName", user.getUserName());
        return new HttpEntity<>(body, headers);
    }
}
```



# 延伸阅读
1. 开启项目需要准备：本地redis服务（localhost:6379）
