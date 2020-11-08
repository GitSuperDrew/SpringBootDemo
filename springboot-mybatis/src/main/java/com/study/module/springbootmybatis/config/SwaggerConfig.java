package com.study.module.springbootmybatis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 常用注解：
 * - @Api()用于类；
 * 表示标识这个类是swagger的资源<br>
 * - @ApiOperation()用于方法；
 * 表示一个http请求的操作<br>
 * - @ApiParam()用于方法，参数，字段说明；
 * 表示对参数的添加元数据（说明或是否必填等）<br>
 * - @ApiModel()用于类
 * 表示对类进行说明，用于参数用实体类接收<br>
 * - @ApiModelProperty()用于方法，字段
 * 表示对model属性的说明或者数据操作更改<br>
 * - @ApiIgnore()用于类，方法，方法参数
 * 表示这个方法或者类被忽略<br>
 * - @ApiImplicitParam() 用于方法
 * 表示单独的请求参数<br>
 * - @ApiImplicitParams() 用于方法，包含多个 @ApiImplicitParam<br>
 * <p>
 * 具体使用举例说明：
 *
 * @author Administrator
 * @Api() 用于类；表示标识这个类是swagger的资源
 * tags–表示说明
 * value–也是说明，可以使用tags替代
 * @date 2020/11/8 上午 10:42
 */
@Configuration
@EnableSwagger2
@Profile(value = {"dev", "test", "pro", "fat", "uat"})
public class SwaggerConfig extends WebMvcConfigurationSupport {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //"com.study.module.springbootmybatis.controller"， web层所在的目录
                .apis(RequestHandlerSelectors.basePackage("com.study.module.springbootmybatis.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .contact(new Contact("Drew", "http://www.baidu.com", "123@163.COM"))
                .title("springboot利用swagger构建api文档---数据源管理系统")
                .description("简单优雅的restfun风格")
                .termsOfServiceUrl("http://localhost:8080/")
                .version("1.0")
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //这些配置要有，我遇到了如果不配置，swagger一直是 404的问题
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        // 解决 SWAGGER 404报错
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
