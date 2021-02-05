# 一、工程简介
> Spring2.X新系列的框架搭建 >>> WebFlux + RESTFul风格。

# 二、搭建步骤
1. 引入核心依赖
```xml
<dependencies>
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-web</artifactId>
   </dependency>
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-webflux</artifactId>
   </dependency>
   <dependency>
       <groupId>org.projectlombok</groupId>
       <artifactId>lombok</artifactId>
       <optional>true</optional>
   </dependency>
</dependencies>
```
2. 定义测试用的实体类`User.java`
```java
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String username;
    private String realName;
    private String password;
    private Integer age;
    private String sex;
}
```
3. 定义DAO层
```java
@Repository
public class UserRepository {
    /**
     * 可以替换成 ORM框架（mybatis的*Mapper）
     */
    private ConcurrentMap<Long, User> repository = new ConcurrentHashMap<>();

    private static final AtomicLong idGenerator = new AtomicLong(0);

    public Long save(User user) {
        Long id = idGenerator.incrementAndGet();
        user.setId(id);
        repository.put(id, user);
        return id;
    }

    public Collection<User> findAll() {
        return repository.values();
    }

    public User findUserById(Long id) {
        return repository.get(id);
    }

    public Long updateUser(User user) {
        repository.put(user.getId(), user);
        return user.getId();
    }

    public Long deleteUser(Long id) {
        repository.remove(id);
        return id;
    }
}
```
4. 定义 Service层（即 *Handler）
```java
@Component
public class UserHandler {

    private final UserRepository userRepository;

    @Autowired
    public UserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<Long> save(User user) {
        return Mono.create(userMonoSink -> userMonoSink.success(userRepository.save(user)));
    }

    public Mono<User> findUserById(Long id) {
        return Mono.justOrEmpty(userRepository.findUserById(id));
    }

    public Flux<User> findAllUser() {
        return Flux.fromIterable(userRepository.findAll());
    }

    public Mono<Long> modifyUser(User user) {
        return Mono.create(userMonoSink -> userMonoSink.success(userRepository.updateUser(user)));
    }

    public Mono<Long> deleteUser(Long id) {
        return Mono.create(userMonoSink -> userMonoSink.success(userRepository.deleteUser(id)));
    }
}
```
5. 控制层（`UserWebFluxController.java`）
```java
@RestController
@RequestMapping(path = "/user")
public class UserWebFluxController {

    @Autowired
    private UserHandler userHandler;

    @GetMapping(path = "/{id}")
    public Mono<User> findUserById(@PathVariable("id") Long id) {
        return userHandler.findUserById(id);
    }

    @GetMapping()
    public Flux<User> findAllUser() {
        return userHandler.findAllUser();
    }

    @PostMapping()
    public Mono<Long> saveUser(@RequestBody User user) {
        return userHandler.save(user);
    }

    @PutMapping()
    public Mono<Long> modifyUser(@RequestBody User user) {
        return userHandler.modifyUser(user);
    }

    @DeleteMapping(path = "/{id}")
    public Mono<Long> deleteUser(@PathVariable(value = "id") Long id) {
        return userHandler.deleteUser(id);
    }
}
```


6. 测试步骤
   1. POST 请求 → 新增
      ```text
      请求地址：http://localhost:8080/user
      请求参数如下：（raw/json）
      {
           "username": "Drew",
           "age": 25,
           "sex": "男",
           "realName": "杜鲁",
           "password": 123
       }
      ```
   2. PUT  请求 → 修改
      ```text
      请求地址：http://localhost:8080/user
      请求参数如下：（raw/json）
      {
           "username": "DrewPlus",
           "age": 23,
           "sex": "男",
           "realName": "杜鲁PLUS",
           "password": 123
       }
      ```
   3. GET  请求 👉 查询
      ```text
      (1)请求地址：http://localhost:8080/user/3
      返回数据如下：
      {
           "id": 3,
           "username": "Drew",
           "realName": "杜鲁",
           "password": "123",
           "age": 25,
           "sex": "男"
       }
      (2)请求所有：http://localhost:8080/user
      返回数据如下：
      [
         {
            "id": 1,
            "username": "Drew Plus",
            "realName": "杜鲁",
            "password": "123",
            "age": 25,
            "sex": "男"
         },
         {
            "id": 2,
            "username": "Drew",
            "realName": "杜鲁",
            "password": "123",
            "age": 25,
            "sex": "男"
         },
         {
            "id": 3,
            "username": "Drew",
            "realName": "杜鲁",
            "password": "123",
            "age": 25,
            "sex": "男"
         }
      ]
      ```
   4. DELETE 请求 → 删除
      ```text
      请求地址：http://localhost:8080/user/1
      返回数据：1 （即用户ID）
      ```

# 三、延伸阅读

