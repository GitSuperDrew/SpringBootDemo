package com.study.module.springbootmybatis.config;

import io.swagger.annotations.ApiOperation;
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
public class SwaggerConfig {
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
                .contact(new Contact("Drew", "https://www.baidu.com", "123456@foxmail.com"))
                .version("1.0")
                .build();
    }

}
