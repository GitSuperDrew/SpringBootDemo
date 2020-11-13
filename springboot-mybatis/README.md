# SpringBoot基于Mybatis学习笔记
## 一、 SpringBoot 整合 Mybatis 框架
> 引入：学无止尽，精益求精。
### 1. 引入相关依赖
```xml
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.1.3</version>
</dependency>
```

### 2. 配置application.properties文件
```properties
# 应用名称
spring.application.name=springboot-mybatis
# 应用服务 WEB 访问端口
server.port=8080
# spring 静态资源扫描路径
spring.resources.static-locations=classpath:/static/
# 数据库驱动：
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
# 数据源名称
spring.datasource.name=defaultDataSource
# 数据库连接地址
spring.datasource.url=jdbc:mysql://localhost:3306/zero?serverTimezone=UTC
# 数据库用户名&密码：
spring.datasource.username=root
spring.datasource.password=root123456
# 访问template下的html文件需要配置模板
spring.thymeleaf.prefix.classpath=classpath:/templates/
# 是否启用缓存
spring.thymeleaf.cache=false
# 模板文件后缀
spring.thymeleaf.suffix=.html
# 模板文件编码
spring.thymeleaf.encoding=UTF-8

## 重点配置 SpringBoot Mybatis
mybatis.type-aliases-package=com.student.module.springbootmybatis.entity
mybatis.mapper-locations=classpath:/mapper/*Dao.xml
#开启 mybatis 查询日志
mybatis.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl
```

### 3. 利用IDEA插件EasyCode，生成mybatis相关接口文档
> 安利一个mybatis快捷生成插件：easycode  （idea）
使用教程请 😀[百度搜索](http://www.baidu.com)

注意：
1. 需要注意配置 SpringBootApplication启动类；
    ```java
    @MapperScan(value = {"com.study.module.springbootmybatis.dao"})
    public class SpringbootMybatisApplication {
     //...
    }
    ```
2. 需要在生成的 `*Dao.xml` 上加入注解 `@Repository` 或者 `@Mapper` ；
3. 配置spingboot-mybatis相关的属性
    ```properties
    ## 重点配置 SpringBoot Mybatis
    mybatis.type-aliases-package=com.student.module.springbootmybatis.entity
    mybatis.mapper-locations=classpath:/mapper/*Dao.xml
    #开启 mybatis 查询日志
    mybatis.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl
    ```
4. 由于初始化项目的时候引入一些很多的依赖包（lombok、rest docs、thymeleaf、validation等），
例如 `Lombok` 工具，你可以优化下 `entity` 包下的DO;利用`Lombok`提供的注解，简化实体类DO相关的文件的内容。

### 4. Mybatis类型处理器
> 下面以处理 性别的类型处理器为例子
1. 新建一个枚举类 `SexEnum.java`;
2. 为了实力类与之前的不冲突，新建一个新的DO：`Teacher.java`;
3. `application.properties` 添加相关配置；
    ```properties
    #配置 typeHandler 的扫描包
    mybatis.type-handlers-package=com.study.module.springbootmybatis.handler
    ```
4. 新建类型处理器 `SexTypeHandler.java` ；
5. 对实体类`TeacherDO.java`可以添加注解`@Alias` 方便 `*Mapper.xml` 中使用（可选）；
6. `TeacherController.java`提供一个新的测试接口`getByIdLogic(...)`。

### 5. 参数验证
1. 添加依赖；
    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    ``` 
2. 安利：IDEA`插件AnyRule`；（用于快速得到正则表达式的插件）
3. 对 DTO 或 VO 进行中的每个需要验证的属性加入对应的验证注解（spring-validation），例如：`TeacherDO.java`
4. controller 层需要在保存或者修改的操作时，需要写成 `@Valid Teacher teacher` 
> 相关博客学习推荐：[验证注解@Pattern](https://blog.csdn.net/qq_36927265/article/details/87864026)
> 

### 6. 配置 API接口文档 `Swagger3.0`
> 相关博客学习文档：[知乎](https://zhuanlan.zhihu.com/p/161947638)
1. 引入 swagger3.0 的依赖, 并移除 `Swagger2.x`相关依赖；
    ```xml
    <dependency>
         <groupId>io.springfox</groupId>
          <artifactId>springfox-boot-starter</artifactId>
          <version>3.0.0</version>
    </dependency>
    ```
2. Application上面加入`@EnableOpenApi`注解；
    ```java
    @EnableOpenApi // 重点注解
    @SpringBootApplication
    @MapperScan(value = {"com.study.module.springbootmybatis.dao"})
    public class SpringbootMybatisApplication {
        //...
    }
    ```
3. Swagger3Config的配置；(将 swagger2.x 的配置文件调整为 swagger3.0 的相关配置)
    ```java
    @Configuration
    public class Swagger3Config {
        @Bean
        public Docket createRestApi() {
            return new Docket(DocumentationType.OAS_30)
                    .apiInfo(apiInfo())
                    .select()
                    .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                    .paths(PathSelectors.any())
                    .build();
        }
    
        private ApiInfo apiInfo() {
            return new ApiInfoBuilder()
                    .title("Swagger3接口文档")
                    .description("更多请咨询服务开发者Ray。")
                    .contact(new Contact("Ray。", "http://www.ruiyeclub.cn", "ruiyeclub@foxmail.com"))
                    .version("1.0")
                    .build();
        }
    }
    ```
4. 注解使用说明：见[附件](#附件)
5. 访问接口文档的地址从 swagger2.x的`port/swagger-ui.html` 变成了 swagger3.0的`port/swagger-ui/index.html`或者`port/swagger-ui/`;

### 7. Swagger3.0整合 Knife4j 美化接口文档
> 官方地址：[Knife4j](https://gitee.com/xiaoym/knife4j)
1. 引入依赖
    ```xml
    <dependency>
        <groupId>com.github.xiaoymin</groupId>
        <artifactId>knife4j-spring-boot-starter</artifactId>
        <!-- 由于我这里时swagger3.0，所以引入了3.0.X的版本-->
        <version>3.0.1</version>
    </dependency>
    ```
2. Application添加注解 `@EnableKnife4j`
    ```java
    @EnableOpenApi
    @EnableKnife4j  // 关键注解
    @SpringBootApplication
    @MapperScan(value = {"com.study.module.springbootmybatis.dao"})
    public class SpringbootMybatisApplication {
    
        public static void main(String[] args) {
            SpringApplication.run(SpringbootMybatisApplication.class, args);
        }
    
    }
    ```
3. 访问地址：
    - swagger3访问地址依赖保留了，（`port/swagger-ui/`）
    - Knife4j-swagger3 访问地址：`http://host:port/doc.html#/plus`;

### 8. `mybatis-pageHelper` 分页插件
1. 引入依赖：
    ```xml
    <!--mybatis-pagehelper 分页插件
        官方地址：https://github.com/pagehelper/Mybatis-PageHelper/blob/master/wikis/en/HowToUse.md
    -->
    <dependency>
        <groupId>com.github.pagehelper</groupId>
        <artifactId>pagehelper-spring-boot-starter</artifactId>
        <version>1.3.0</version>
    </dependency>
    ```
2. 新建接口用于测试（`TeacherController.page`）；
3. 提供了两个分页实体类（`PageVO.java`, `PageForm.java`）；
4. 引入了 `Fastjson` 依赖包；
    ```xml
    <!-- https://mvnrepository.com/artifact/com.alibaba/fastjson -->
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>fastjson</artifactId>
        <version>1.2.73</version>
    </dependency>
    ```
5. 引入了分页插件 `PageHelper` 依赖包；
    ```xml
    <dependency>
        <groupId>com.github.pagehelper</groupId>
        <artifactId>pagehelper-spring-boot-starter</artifactId>
        <version>1.3.0</version>
    </dependency>
    ```

### 9. 全局异常捕获处理 + 统一返回值
1. 创建枚举类 `ResultEnum.java`；
2. 提供全局自定义异常，如 `CustomException.java`；
3. 提供全局异常捕获类，如 `GlobalExceptionHandling.java`；
4. 提供必要的工具类，如 `MethodUtil`；
5. 提供必要的测试接口，如 `TeacherController#delete`；
6. 如何使用全局异常捕获工具，请看：`TeacherServiceImpl#deleteTeacher`；
7. 其他接口例如`保存 save`，请自行更改成全局异常捕获；

### 10. Web-验证码工具
> 学习博客地址：[头条](https://www.toutiao.com/i6892962386928468484/?tt_from=weixin&utm_campaign=client_share&wxshare_count=1&timestamp=1604899587&app=news_article&utm_source=weixin&utm_medium=toutiao_android&use_new_style=1&req_id=202011091326270100270510141302F806&group_id=6892962386928468484)
1. 引入依赖`kaptcha`;
    ```xml
    <!-- https://mvnrepository.com/artifact/com.github.penggle/kaptcha -->
    <dependency>
        <groupId>com.github.penggle</groupId>
        <artifactId>kaptcha</artifactId>
        <version>2.3.2</version>
    </dependency>
    ```
2. 添加相关配置文件`KaptchaConfig.java`；
    ```java
    import com.google.code.kaptcha.impl.DefaultKaptcha;
    import com.google.code.kaptcha.util.Config;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    
    import java.util.Properties;
    
    /**
     * @author Administrator
     * @date 2020/11/9 下午 1:33
     */
    @Configuration
    public class KaptchaConfig {
        @Bean
        public DefaultKaptcha getDefaultKaptcha(){
            DefaultKaptcha captchaProducer = new DefaultKaptcha();
            Properties properties = new Properties();
            properties.setProperty("kaptcha.border", "no");
            properties.setProperty("kaptcha.border.color", "105,179,90");
            properties.setProperty("kaptcha.textproducer.font.color", "blue");
            properties.setProperty("kaptcha.image.width", "110");
            properties.setProperty("kaptcha.image.height", "36");
            properties.setProperty("kaptcha.textproducer.font.size", "30");
            properties.setProperty("kaptcha.session.key", "code");
            properties.setProperty("kaptcha.textproducer.char.length", "4");
            properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
            properties.setProperty("kaptcha.textproducer.char.string", "0123456789ABCEFGHIJKLMNOPQRSTUVWXYZ");
            properties.setProperty("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.WaterRipple");
            properties.setProperty("kaptcha.noise.color", "black");
    //        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.DefaultNoise");
            properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");
            properties.setProperty("kaptcha.background.clear.from", "232,240,254");
            properties.setProperty("kaptcha.background.clear.to", "232,240,254");
            properties.setProperty("kaptcha.textproducer.char.space", "3");
            Config config = new Config(properties);
            captchaProducer.setConfig(config);
            return captchaProducer;
    
        }
    }    
    ```
3. 书写生成验证码的控制层（`KaptchaController.java`）； 
4. 页面显示的代码
    ```html
    <div class="form-group">
        <div class="input-group">
            <input class="form-control" type="text" autocomplete="new-password" placeholder="验证码" required maxlength="4" v-model="verifyCode">
            <span class="input-group-btn">
                <img id="captcha_img" alt="验证码" title="点击更换" onclick="refreshKaptcha()" src="/kaptcha" />
            </span>
        </div>    
    </div>
    ```
   更新验证码JavaScript方法
   ```javascript
    function refreshKaptcha() {
        document.getElementById('captcha_img').src="/kaptcha?"+ Math.random();
    }
    ```
5. 验证校验码；
    ```text
    // 获取session中生成的校验码
    String kaptchaCode = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
    
    // 获取页面提交的验证码
    String verifyCode = request.getParameter("verifyCode");
    
    //校验验证码
    if (!StringUtils.equalsIgnoreCase(verifyCode, kaptchaCode)){
        throw new Exception("校验码错误！");
    }
    ```
6. 至此，SpringBoot集成`Kaptcha`验证码工具类完毕，请自行测试；

### 11. mybatis 自定义`拦截器插件interceptor`
1. mybatis 中拦截器的类型有4种：

    |   拦截器 |  说明 |
    | --- | --- |
    |   Executor            |   拦截执行器的方法        |
    |   ParameterHandler    |   拦截参数的处理          |
    |   ResultHandler       |   拦截结果集的处理        |
    |   StatementHandler    |   拦截 SQL 语法构建的处理。 |
    
    - Executor: 拦截执行器的方法。
    - ParameterHandler：拦截参数的处理。
    - ResultHandler：拦截结果集的处理。
    - StatementHandler：拦截 SQL 语法构建的处理。

2. 拦截器规则：
    1. Intercepts 注解需要一个 Signature （拦截点）参数数组。通过 Signature 来指定拦截哪个对象里面的哪个方法。
    2. `@Intercepts` 注解定义如下：
        ```java
        @Documented
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.TYPE)
        public @interface Intercepts {
            /* 定义拦截：只有符合拦截点的条件才会进入到拦截器 */
            Signature[] value();
        }
        ```
.....

### 12. SpringBoot-Mybatis下的文件上传
1. 确保存在SpringBootWeb依赖：
    ```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    ```
2. application.properties 添加相关配置：
    ```properties
    # 上传文件所需的配置
    spring.servlet.multipart.max-file-size=10MB
    spring.servlet.multipart.max-request-size=10MB
    ```
3. 书写优先级最高的配置 `*Config.java` 
    ```java
    import org.springframework.context.annotation.Configuration;
    import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
    import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
    
    /**
     * 配置虚拟路径映射（这一步很重要，我们将文件上传到服务器上时，我们需要将我们的请求路径和服务器上的路径进行对应，不然很有可能文件上传成功，但访问失败。）
     * <p>对应关系需要自己去定义，如果访问失败，可以试着打印下路径，看看是否漏缺了分隔符</p>
     * <p>如果 addResourceHandler 不要写成处理 /**, 这样写会被拦截掉其他请求。</p>
     *
     * @author Administrator
     * @date 2020/11/13 下午 8:23
     */
    @Configuration
    public class MvcConfig implements WebMvcConfigurer {
    
        private static final String UPLOADED_FOLDER = System.getProperty("user.dir");
    
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/upload/**")
                    .addResourceLocations("file:///" + UPLOADED_FOLDER + "/");
        }
    }
    ```
4. 控制层：
    ```java
    import org.springframework.stereotype.Controller;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RequestParam;
    import org.springframework.web.multipart.MultipartFile;
    import org.springframework.web.servlet.mvc.support.RedirectAttributes;
    
    import java.io.IOException;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    
    /**
     * 文件上传
     *
     * @author Administrator
     * @date 2020/11/13 下午 8:19
     */
    @Controller
    @RequestMapping(value = "/fileUpload")
    public class FileUploadController {
    
        private static final String UPLOADED_FOLDER = System.getProperty("user.dir");
    
        @GetMapping("/redirectFilePage")
        public String index() {
            return "file";
        }
    
        @PostMapping("/upload")
        public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                       RedirectAttributes redirectAttributes) throws IOException {
    
            if (file.isEmpty()) {
                redirectAttributes.addFlashAttribute("msg", "文件为空,请选择你的文件上传");
                return "redirect:uploadStatus";
            }
            saveFile(file);
            redirectAttributes.addFlashAttribute("msg", "上传文件" + file.getOriginalFilename() + "成功");
            redirectAttributes.addFlashAttribute("url", "/upload/" + file.getOriginalFilename());
            return "redirect:uploadStatus";
        }
    
        private void saveFile(MultipartFile file) throws IOException {
            Path path = Paths.get(UPLOADED_FOLDER + "/" + file.getOriginalFilename());
            file.transferTo(path);
        }
    
        @GetMapping("/uploadStatus")
        public String uploadStatus() {
            return "uploadStatus";
        }
    }
    ```
5. 添加测试页面
    1. 主页面：file.html
    ```html
    <html>
    <!--suppress ALL-->
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>文件上传界面</title>
    </head>
    <body>
    <div>
        <form method="POST" enctype="multipart/form-data" action="/fileUpload/upload">
            <table>
                <tr>
                    <td><input type="file" name="file"/></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" value="上传"/></td>
                </tr>
            </table>
        </form>
    
    </div>
    </body>
    </html>
    
    ```
6. 上传成功与否的状态页面
    ```html
    <!--suppress ALL-->
    <html xmlns:th="http://www.thymeleaf.org">
    
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>文件上传界面</title>
    </head>
    <body>
    <div th:if="${msg}">
        <h2 th:text="${msg}"/>
    </div>
    <div>
        <img src="" th:src="${url}" alt="">
    </div>
    </body>
    </html>
    
    ```
7. 测试文件上传功能；
   1. 请求URL：`http://localhost:8080/fileUpload/redirectFilePage`
   2. 选择文件，如果上传文件成功，则文件将保存到与src同一级别的目录中。
8. 提供SpringBoot 的 Restful API 接口。（见`FileUploadRestController.java`）




## 附件
### 1. Swagger2.x 的相关注解说明：
```text
 常用注解：
 - @Api()用于类；
        表示标识这个类是swagger的资源
 - @ApiOperation()用于方法；
        表示一个http请求的操作
 - @ApiParam()用于方法，参数，字段说明；
        表示对参数的添加元数据（说明或是否必填等）
 - @ApiModel()用于类
        表示对类进行说明，用于参数用实体类接收
 - @ApiModelProperty()用于方法，字段
        表示对model属性的说明或者数据操作更改
 - @ApiIgnore()用于类，方法，方法参数
        表示这个方法或者类被忽略
 - @ApiImplicitParam() 用于方法
        表示单独的请求参数
 - @ApiImplicitParams() 用于方法，包含多个 @ApiImplicitParam
 <p>
```

### 2. Swagger3.0 的相关注解说明：
```text
@Api：用在请求的类上，表示对类的说明
    tags="说明该类的作用，可以在UI界面上看到的注解"
    value="该参数没什么意义，在UI界面上也看到，所以不需要配置"

@ApiOperation：用在请求的方法上，说明方法的用途、作用
    value="说明方法的用途、作用"
    notes="方法的备注说明"

@ApiImplicitParams：用在请求的方法上，表示一组参数说明
    @ApiImplicitParam：用在@ApiImplicitParams注解中，指定一个请求参数的各个方面
        name：参数名
        value：参数的汉字说明、解释
        required：参数是否必须传
        paramType：参数放在哪个地方
            · header --> 请求参数的获取：@RequestHeader
            · query --> 请求参数的获取：@RequestParam
            · path（用于restful接口）--> 请求参数的获取：@PathVariable
            · div（不常用）
            · form（不常用）    
        dataType：参数类型，默认String，其它值dataType="Integer"       
        defaultValue：参数的默认值

@ApiResponses：用在请求的方法上，表示一组响应
    @ApiResponse：用在@ApiResponses中，一般用于表达一个错误的响应信息
        code：数字，例如400
        message：信息，例如"请求参数没填好"
        response：抛出异常的类

@ApiModel：用于响应类上，表示一个返回响应数据的信息
            （这种一般用在post创建的时候，使用@RequestBody这样的场景，
            请求参数无法使用@ApiImplicitParam注解进行描述的时候）
    @ApiModelProperty：用在属性上，描述响应类的属性
```

### 3. SpringBoot 自定义Banner
1. 在 `src/main/resources` 目录下，创建一个Banner.txt；
2. 在 以下网址中生成你想要的 `banner` 图标；
    - http://patorjk.com/software/taag
    - http://www.network-science.de/ascii/
    - http://www.degraeve.com/img2txt.php
3. 讲解博客地址：[SpringBoot自定义Banner](https://www.jianshu.com/p/a53f324c92f2)
4. 启动项目试试，你会发现奇迹出现！（^_^）
5. 如果你想在测试环境中省略掉banner，你可以删除banner.txt中所有的内容保存重启即可；
6. 如果你想对banner进行更个性化的修饰：下面的这些属性可以帮到你：
    ```properties
    spring.banner.charset=UTF8 #默认配置
    spring.banner.location=classpath:banner.txt #默认配置
    spring.banner.image.bitdepth=4 #默认配置
    spring.banner.image.height=76
    spring.banner.image.invert=false #默认配置
    spring.banner.image.location=classpath:banner.gif #默认配置
    spring.banner.image.margin=2 #默认配置
    spring.banner.image.pixelmode=TEXT #默认配置
    spring.banner.image.width=76 #默认配置
    ```

### 4. Kinfe4j 使用官方文档：
1. 官方文档地址: [https://doc.xiaominfo.com/knife4j/autoEnableKnife4j.html](https://doc.xiaominfo.com/knife4j/autoEnableKnife4j.html)
2. Gitee地址: [https://gitee.com/xiaoym/knife4j](https://gitee.com/xiaoym/knife4j)
