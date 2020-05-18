
## ⚠ 注意：此项目存在问题，
1. `Spring Boot` 整合 `sqlhelper` 过程中，出现问题：
2. 问题如下：
`java.lang.RuntimeException: Can't find any supported JSON libraries : [gson, jackson, fastjson], check you classpath
 has one of these jar pairs: [fastjson, easyjson-fastjson], [gson, easyjson-gson], [jackson, easyjson-jackson]`
 > 目前未解决，项目运行不起来！！
3. 迟迟问题为解决的原因：
    - 社区不活跃；
    - 讲解文档不详细


## 项目引入技术记录说明
1. `Spring Boot` 读取配置文件属性：见 `ReadYamlController.java`
2. `Spring Boot` 批量读取配置文件赋值给实体类：见 `dataConfigBean.java` 
    * 第一步：在配置文件`application.yaml`中定义示例信息：
    ```yaml
        my:
          name: drew
          password: 123456
          age: 12
          number: ${random.int}
          uuid: ${random.uuid}
          max: ${random.int(10)}
          value: ${random.value}
          greeting: hi, I'm ${my.name}
    ```
    * 第二步：书写 ConfigBean ：`MyInfoConfigBean.java`
    ```java
    @ConfigurationProperties(prefix = "my")
    @Component
    @Data
    public class MyInfoConfigBean {
        private String name;
        private int age;
        private int number;
        private String uuid;
        private int max;
        private String value;
        private String greeting;
    }
    ```
    * 第三步：写一个 Controller 类：`MyInfoController.java`
    ```java
    @RequestMapping(value = "/showInfo")
    @RestController
    @EnableConfigurationProperties({MyInfoConfigBean.class})
    public class MyInfoController {
        @Autowired
        MyInfoConfigBean myInfoConfigBean;
    
        /**
         * 利用实体类接收，批量读取配置信息
         * URL: http://localhost:8080/showInfo/myInfo
         *
         * @return
         */
        @RequestMapping(value = "/myInfo")
        public String myInfo() {
            return myInfoConfigBean.getGreeting() + "-" + myInfoConfigBean.getName() + "-" + myInfoConfigBean.getUuid() + "-" + myInfoConfigBean.getMax();
        }
    }
    ```
    * 第四步：启动项目，浏览器访问：[http://localhost:8080/showInfo/myInfo](http://localhost:8080/showInfo/myInfo)


## 自定义配置文件`test.properties`
1. 再`src/main/resources`目录下新建 `test.properties`，添加下面内容
```yaml
userdemo.name=drew
userdemo.age=12
```
2. 新建一个 实体类用户接收新的配置文件的内容 `UserConfigBean.java`
```java
@Configuration
@PropertySource(value = "classpath:test.properties")
@ConfigurationProperties(prefix = "userdemo")
@Data
public class UserConfigBean {
    private String name;
    private int age;
}
```
3. 新建一个 controller类用于测试效果：`UserInfoController.java`
```java
@RestController
@RequestMapping(value = "/test")
@EnableConfigurationProperties({UserConfigBean.class})
public class UserInfoController {
    @Autowired
    UserConfigBean userConfigBean;

    /**
     * URL: http://localhost:8080/test/userInfo
     *
     * @return
     */
    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo() {
        return userConfigBean.getName() + "/" + userConfigBean.getAge();
    }
}
```


## 多个环境的配置文件
1. 环境类型的配置文件命名如下：
    * 测试环境： `application-test.properties` / `application-test.yaml`
    * 开发环境：`application-dev.properties` / `application-dev.yaml`
    * 预发环境：`application-uat.properties` / `application-uat.yaml`
    * 生成环境：`application-prod.properties` / `application-prod.yaml`
2. 在`src/main/resources`目录下，分别建立这四个文件，文件的内容大部分一样的，只需要修改数据库的连接信息；
3. 在 `application.yaml` 文件中，加入：
```yaml
spring:
    profiles:
      active: dev
```
而 `application.yaml` 文件本省的端口配置信息，则需要删除；例如：`server.port=8080` 需要放入到对应环境下的配置文件中，其他信息也同样如此。
4. 注意：各种环境如果是一样的配置，则可以将共有的配置信息放入到 `application.yaml` 文件中。（即：只有在不同环境中存在区别的配置才需要配置）


## ♥ 运行状态监控 Actuator
    Spring Boot 提供了运行状态的监控功能， Actuator 的监控数据可以通过 `REST`、远程 shell 和 JMX 方式获得。
1. 引入 Actuator 依赖：（并及时刷新 Maven）
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
```
2. 在配置文件中书写 `Actuator` 相关的配置信息：
```yaml
management:
  endpoints:
    web:
      exposure:
        include: "*"
  endpoint:
    health:
      show-details: ALWAYS
    shutdown:
      enabled: true
```
3. 访问端口，进行测试; [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)
4. 结果展示：`{"status":"UP","components":{"db":{"status":"UP","details":{"database":"MySQL","result":1,"validationQuery":"/* ping */ SELECT 1"}},"diskSpace":{"status":"UP","details":{"total":53696524288,"free":33000722432,"threshold":10485760}},"ping":{"status":"UP"}}}`
5. **拓展**：还有许多和`Actuator`相关的端口：例如 /autoconfig 是获取一份自动配置报告，记录那些自动配置条件通过了，哪些没有通过。




