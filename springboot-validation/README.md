# 工程简介
#### 一. 核心maven依赖引入
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.72</version>
</dependency>
<dependency>
    <groupId>commons-io</groupId>
    <artifactId>commons-io</artifactId>
    <version>2.6</version>
</dependency>
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
    <version>3.1</version>
</dependency>
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

#### 二. application.properties 配置文件
```properties
# 应用名称
spring.application.name=springboot-validation
# 应用端口
server.port=8080
# 热部署
spring.devtools.restart.enabled=true
```

#### 三. 统一异常处理相关
1. 统一返回实体类 `ResultVO.java` 
    ```java
    @Data
    @EqualsAndHashCode(callSuper = false)
    @NoArgsConstructor
    public class ResultVO extends HashMap<String, Object> {
        private static final long serialVersionUID = 1L;
    
        public static final String CODE_TAG = "code";
    
        public static final String MSG_TAG = "msg";
    
        public static final String DATA_TAG = "data";
    
        /**
         * 状态类型
         */
        public enum Type {
            /**
             * 成功
             */
            SUCCESS(1),
            /**
             * 警告
             */
            WARN(2),
            /**
             * 错误
             */
            ERROR(0),
            /**
             * 无权限
             */
            UNAUTH(3),
            /**
             * 未登录、登录超时
             */
            UNLOGIN(4);
            private final int value;
    
            Type(int value) {
                this.value = value;
            }
    
            public int value() {
                return this.value;
            }
        }
    
        /**
         * 状态类型
         */
        private Type type;
    
        /**
         * 状态码
         */
        private int code;
    
        /**
         * 返回内容
         */
        private String msg;
    
        /**
         * 数据对象
         */
        private Object data;
    
        /**
         * 初始化一个新创建的 ResultVO 对象
         *
         * @param type 状态类型
         * @param msg  返回内容
         */
        public ResultVO(Type type, String msg) {
            super.put(CODE_TAG, type.value);
            super.put(MSG_TAG, msg);
        }
    
        /**
         * 初始化一个新创建的 ResultVO 对象
         *
         * @param type 状态类型
         * @param msg  返回内容
         * @param data 数据对象
         */
        public ResultVO(Type type, String msg, Object data) {
            super.put(CODE_TAG, type.value);
            super.put(MSG_TAG, msg);
    //         数据为空的时候，还是需要把参数传给前台
    //        if (StringUtils.isNotNull(data)) {
    //            super.put(DATA_TAG, data);
    //        }
            super.put(DATA_TAG, data);
        }
    
    
        /**
         * 返回成功消息
         *
         * @return 成功消息
         */
        public static ResultVO success() {
            return ResultVO.success("操作成功");
        }
    
        /**
         * 返回成功数据
         *
         * @return 成功消息
         */
        public static ResultVO success(Object data) {
            return ResultVO.success("操作成功", data);
        }
    
        /**
         * 返回成功消息
         *
         * @param msg 返回内容
         * @return 成功消息
         */
        public static ResultVO success(String msg) {
            return ResultVO.success(msg, null);
        }
    
    
        /**
         * 返回成功消息
         *
         * @param msg  返回内容
         * @param data 数据对象
         * @return 成功消息
         */
        public static ResultVO success(String msg, Object data) {
            return new ResultVO(Type.SUCCESS, msg, data);
        }
    
        /**
         * 返回警告消息
         *
         * @param msg 返回内容
         * @return 警告消息
         */
        public static ResultVO warn(String msg) {
            return ResultVO.warn(msg, null);
        }
    
        /**
         * 返回警告消息
         *
         * @param msg  返回内容
         * @param data 数据对象
         * @return 警告消息
         */
        public static ResultVO warn(String msg, Object data) {
            return new ResultVO(Type.WARN, msg, data);
        }
    
    
        /**
         * 返回错误消息
         *
         * @return
         */
        public static ResultVO error() {
            return ResultVO.error("操作失败");
        }
    
        /**
         * 返回错误消息
         *
         * @param msg 返回内容
         * @return 警告消息
         */
        public static ResultVO error(String msg) {
            return ResultVO.error(msg, null);
        }
    
        /**
         * 返回错误消息
         *
         * @param msg  返回内容
         * @param data 数据对象
         * @return 警告消息
         */
        public static ResultVO error(String msg, Object data) {
            return new ResultVO(Type.ERROR, msg, data);
        }
    
    
        /**
         * 无权限返回
         *
         * @return
         */
        public static ResultVO unAuth() {
            return new ResultVO(Type.UNAUTH, "您没有访问权限！", null);
        }
    
        /**
         * 无权限
         */
        public static ResultVO unAuth(String msg) {
            return new ResultVO(Type.UNAUTH, msg, null);
        }
    
        /**
         * 未登录或登录超时。请重新登录
         */
        public static ResultVO unLogin() {
            return new ResultVO(Type.UNLOGIN, "未登录或登录超时。请重新登录！", null);
        }
    
        public static class Success {
    
            public static ResultVO data(Object data) {
                return new ResultVO(Type.SUCCESS, "操作成功 Operation Successful", data);
            }
    
            public static ResultVO outMsg(String msg) {
                return new ResultVO(Type.SUCCESS, msg, null);
            }
    
            public static ResultVO outMsgAndData(String msg, Object data) {
                return new ResultVO(Type.SUCCESS, msg, data);
            }
        }
    
    
        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                    .append("code", getCode())
                    .append("msg", getMsg())
                    .append("data", getData())
                    .toString();
        }
    }
    
    ```

2. 相关捕获类`GlobalExceptionHandler.java`
    ```java
    @Slf4j
    @RestControllerAdvice
    public class GlobalExceptionHandler {
    
        /**
         * 请求方式不支持
         */
        @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
        public ResultVO handleException(HttpRequestMethodNotSupportedException e) {
            log.error(e.getMessage(), e);
            return ResultVO.error("不支持' " + e.getMethod() + "'请求");
        }
    
        /**
         * 拦截未知的运行时异常
         */
        @ExceptionHandler(RuntimeException.class)
        public ResultVO notFount(RuntimeException e) {
            log.error("运行时异常:", e);
            return ResultVO.error("运行时异常:" + e.getMessage());
        }
    
        /**
         * 系统异常
         */
        @ExceptionHandler(Exception.class)
        public ResultVO handleException(Exception e) {
            log.error(e.getMessage(), e);
            return ResultVO.error("服务器错误，请联系管理员");
        }
    
    
        /**
         * 校验异常
         */
        @ExceptionHandler(value = MethodArgumentNotValidException.class)
        public ResultVO exceptionHandler(MethodArgumentNotValidException e) {
            BindingResult bindingResult = e.getBindingResult();
            StringBuilder errorMsg = new StringBuilder();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                String index = (bindingResult.getAllErrors().indexOf(fieldError) + 1) + ".";
                errorMsg.append(index).append(fieldError.getDefaultMessage()).append("! ");
            }
            return ResultVO.error(errorMsg.toString());
        }
    
        /**
         * 校验异常
         */
        @ExceptionHandler(value = BindException.class)
        public ResultVO validationExceptionHandler(BindException e) {
            BindingResult bindingResult = e.getBindingResult();
            String errorMsg = "";
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errorMsg += fieldError.getDefaultMessage() + "!";
            }
            return ResultVO.error(errorMsg);
        }
    
    
        /**`
         * 校验异常
         */
        @ExceptionHandler(value = ConstraintViolationException.class)
        public ResultVO constraintViolationExceptionHandler(ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();
            List<String> msgList = new ArrayList<>();
            while (iterator.hasNext()) {
                ConstraintViolation<?> cvl = iterator.next();
                msgList.add(cvl.getMessageTemplate());
            }
            return ResultVO.error(String.join(",", msgList));
        }
    
    }
    ```

#### 四. 测试相关
1. 用户添加接口用于测试 `UserController.java`
    ```java
    @RestController
    @RequestMapping(path = "/user")
    public class UserController {
    
        @PostMapping(path = "/add")
        public Object addUser(@RequestBody @Valid UserVO userVO){
            System.out.println("传入的参数为："  + userVO);
            return "^_^ 参数验证成功！";
        }
    }
    ```

2. 测试示例：
    - 请求方式：POST ；
    - URL：http://localhost:8080/user/add
    - 输入参数：
        ```json
        {
            "id": "",
            "username": "Drew119909090((**&",
            "password": "Drew11",
            "bankId": "111122223333ooo",
            "idCard": "430224199607148786000",
            "age": 230,
            "sex": "男",
            "birthday": "2021-01-10 23:23:23",
            "callBackTime": "2021-01-10 23:23:23",
            "mobile": "1370742160700",
            "enabled": true,
            "grade": 10000.999,
            "level": 20000,
            "postalCode": "412414kk"
        }
        ```
    - 返回结果：
        ```json
        {
            "msg": "1.用户昵称限制：最多20字符，包含文字、字母和数字! 2.用户等级异常! 3.银行账号填写错误! 4.手机号格式有误! 5.归还时间必须是将来时间! 6.身份证号填写错误! 7.成绩最高为999.9! 8.年龄最大值为150! 9.用户ID不能为空! 10.非法邮政编码! ",
            "code": 0,
            "data": null
        }
        ```
    - 总结：
        > 1. 返回的结果都被统一异常处理类捕获，说明测试验证成功；<br/>
        > 2. 数据按照指定的参数要求进行验证，且正常返回提示信息；


#### 五、整合 SSL - HTTPS协议访问
1. 第一步：CMD执行命令；
    ```shell script
    keytool -genkey -alias tomcat -keyalg RSA -keysize 2048 -validety 365 -keystore.p12 -keypass 123456 -stroepass 123456 -dname "CN=xingming,OU=danwei,O=zuzhi,L=shi,ST=sheng,C=CN"
    ```
2. 第二步：配置属性（`application.properties`）
    ```properties
    server.http2.enabled=true
    http.port=80
    server.ssl.enabled=true
    server.ssl.key-alias=tomcat
    server.ssl.key-store=classpath:keystore.p12
    server.ssl.key-store-password=123456
    server.ssl.key-store-type=JKS
    ```
3. 配置 http和https都可访问的内容（见`TomcatConfig.java`）
4. 浏览器测试。
    ```text
    浏览器访问 
        https://localhost:8080/user/testHttps
        http://localhost:8080/user/testHttps
    ```





# 延伸阅读
1. 知识引入：[今日头条](https://blog.csdn.net/songguopeng/article/details/98961787)
2. 邮编查询：[http://www.yb21.cn/post/](http://www.yb21.cn/post/)
3. [SpringValidation 最佳实践和原理了解](https://blog.csdn.net/qq_39361915/article/details/109184576)
4. Spring Validation 基本用法：[https://juejin.cn/post/6844903902811275278](https://juejin.cn/post/6844903902811275278)
