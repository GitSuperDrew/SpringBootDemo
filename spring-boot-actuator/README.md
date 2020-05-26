## Spring Boot Actuator 学习
> 提醒： `Spring Boot` 版本为 `1.5.13.RELEASE`
1. 引入`actuator`依赖：
    ```xml
    <!--Spring Boot 的监控管理-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    ```
2. 配置文件：(添加 `actuator` 部分配置)
    ```yaml
    # 设置：监控无需权限验证
    management:
      security:
        enabled: false
    # 禁用 localhost:8080/dump （即 访问 此路径报404，如果允许访问，enabled=true）
    endpoints:
      dump:
        enabled: false
    ```

3. 启动服务，控制台出现有关 `actuator` 的内容：
    * 监控信息
    ![actuator监控信息](images\actuator_console_info.png)
    * 页面验证：
    ![actuator请求结果示例](images\actuator_request_info.png)
    * controller测试：
    ![HelloController测试](images\hellocontroller.png)


