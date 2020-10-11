## 一、Spring Boot 整合 Swagger3
1. 第一步：引入依赖（io.springfox）
    ```xml
    <!--Swagger3的依赖-->
    <dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-boot-starter</artifactId>
        <version>3.0.0</version>
    </dependency>
    ```
2. 第二步：添加配置文件（SwaggerConfig.java）
    ```java
   import io.swagger.annotations.ApiOperation;
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   import springfox.documentation.builders.ApiInfoBuilder;
   import springfox.documentation.builders.PathSelectors;
   import springfox.documentation.builders.RequestHandlerSelectors;
   import springfox.documentation.service.ApiInfo;
   import springfox.documentation.service.Contact;
   import springfox.documentation.spi.DocumentationType;
   import springfox.documentation.spring.web.plugins.Docket;
   
   /**
    * @author Administrator
    * @date 2020/10/9 上午 9:30
    */
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
                   .title("Swagger3 接口文档")
                   .description("更多请咨询相关开发人员")
                   .contact(new Contact("Drew", "开发者的博客论坛", "开发者的邮箱：例如：123@qq.com"))
                   .version("1.0")
                   .build();
       }
   }
    ```
3. 测试案例：见 StudentController.java 类
    * 注意相关注解：@Api 等的用法

## 二、附加小工具
### 1. POI动态导出excel模板
> 指定列和行范围中含有下拉框，提示，批注信息

① 引入依赖
```xml
<!--附加：POI操作excel功能演示-->
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi</artifactId>
    <version>3.9</version>
</dependency>
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>3.9</version>
</dependency>
```
② 书写工具类（PoiTest2.java）<br/>
③ 学习示例：[教程](https://blog.csdn.net/pyl574069214/article/details/52995884)
### 2. 对Excel进行添加水印

