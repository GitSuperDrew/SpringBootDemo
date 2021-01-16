## Spring Boot 整合 H2 数据库

### 教程地址
> [简书](https://www.jianshu.com/p/afcc8fdb5d69)
#### 1. 添加必要的 jar 包
```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<!-- 辅助包-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
```

#### 2. 在 application.properties 中添加必要的配置
```properties
server.port=8080
spring.application.name=springboot-h2

# 数据库及ORM部署
#h2配置
# 启用SQL语句的日志记录
spring.jpa.show-sql=true
# 设置ddl模式
spring.jpa.hibernate.ddl-auto=update
##数据库连接设置
#配置h2数据库的连接地址 （本地嵌入式h2数据库配置：jdbc:h2:D:/Program Files (x86)/H2/data）
spring.datasource.url=jdbc:h2:mem:dbtest
#配置数据库用户名
spring.datasource.username=sa
#配置数据库密码
spring.datasource.password=sa
# 配置JDBC Driver
spring.datasource.driverClassName =org.h2.Driver
##数据初始化设置
# 进行该配置后，每次启动程序，程序都会运行resources/db/schema.sql文件，对数据库的结构进行操作。
spring.datasource.schema=classpath:db/schema.sql
# 进行该配置后，每次启动程序，程序都会运行resources/db/data.sql文件，对数据库的数据操作。
spring.datasource.data=classpath:db/data.sql
##h2 web console设置
#表明使用的数据库平台是h2
spring.datasource.platform=h2
# 进行该配置后，h2 web console 就可以在远程访问了。否则只能在本机访问。
spring.h2.console.settings.web-allow-others=true
#进行该配置，你就可以通过YOUR_URL/h2访问h2 web console。YOUR_URL是你程序的访问URl。
spring.h2.console.path=/h2
#进行该配置，程序开启时就会启动h2 web console。当然这是默认的，如果你不想在启动程序时启动h2 web console，那么就设置为false。
spring.h2.console.enabled=true
```

#### 3. 添加*数据库结构* 和 *数据脚本*
1. resource/db/schema.sql
    ```sql
    create table if not exists USER
    (
        USE_ID        int not null primary key auto_increment,
        USE_NAME      varchar(100),
        USE_SEX       varchar(1),
        USE_AGE       NUMBER(3),
        USE_ID_NO     VARCHAR(18),
        USE_PHONE_NUM VARCHAR(11),
        USE_EMAIL     VARCHAR(100),
        CREATE_TIME   DATE,
        MODIFY_TIME   DATE,
        USE_STATE     VARCHAR(1)
    );
    ```

2. resource/db/data.sql
    ```sql
    INSERT INTO USER (USE_ID,USE_NAME,USE_SEX,USE_AGE,USE_ID_NO,USE_PHONE_NUM,USE_EMAIL,CREATE_TIME,MODIFY_TIME,USE_STATE)
    VALUES(1,'赵一','0',20,'142323198610051234','12345678910','qe259@163.com',sysdate,sysdate,'0');
    
    INSERT INTO USER (USE_ID,USE_NAME,USE_SEX,USE_AGE,USE_ID_NO,USE_PHONE_NUM,USE_EMAIL,CREATE_TIME,MODIFY_TIME,USE_STATE)
    VALUES(2,'钱二','0',22,'142323198610051235','12345678911','qe259@164.com',sysdate,sysdate,'0');
    
    INSERT INTO USER (USE_ID,USE_NAME,USE_SEX,USE_AGE,USE_ID_NO,USE_PHONE_NUM,USE_EMAIL,CREATE_TIME,MODIFY_TIME,USE_STATE)
    VALUES(3,'孙三','1',24,'142323198610051236','12345678912','qe259@165.com',sysdate,sysdate,'0');
    ```

#### 4. 启动项目，浏览器访问
> 项目访问地址：[http://localhost:8080/h2](http://localhost:8080/h2)

#### 附录
1. 本地开发（windows）：见 `H2.java` 测试类
> - 注意：
>   - 下载安装好 `h2 数据库`;
>   - 进入 `/bin/` 启动 `h2.bat` , 保持 CMD命令窗口开启;
>   - 运行 H2.java 主程序方法;(h2本地数据库安装地址，root/root，登录)