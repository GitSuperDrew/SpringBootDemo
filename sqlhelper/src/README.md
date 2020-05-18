
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













## ⚠ 注意：此项目存在问题，
1. `Spring Boot` 整合 `sqlhelper` 过程中，出现问题：
2. 问题如下：
`java.lang.RuntimeException: Can't find any supported JSON libraries : [gson, jackson, fastjson], check you classpath
 has one of these jar pairs: [fastjson, easyjson-fastjson], [gson, easyjson-gson], [jackson, easyjson-jackson]`
 > 目前未解决，项目运行不起来！！
3. 迟迟问题为解决的原因：
    - 社区不活跃；
    - 讲解文档不详细
