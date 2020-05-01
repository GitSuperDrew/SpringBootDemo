package com.study.module.mybatis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger启动配置类
 *
 * @author Drew
 * @date 2020年4月19日 09点00分
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    /**
     * swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
     *
     * @return 配型swagger2接口文档的摘要
     */
    @Bean
    public Docket createRestfulApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                //调用apiInfo方法,创建一个ApiInfo实例,里面是展示在文档页面信息内容
                .apiInfo(apiInfo())
                .pathMapping("/")
                .select()
                //暴露接口地址的包路径  例如：com.lance.learn.springbootswagger.controller
                .apis(RequestHandlerSelectors.basePackage("com.study.module.mybatis.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 构建 api文档的详细信息函数,注意这里的注解引用的是哪个
     *
     * @return Swagger2 配置的RESTful Api 信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 接口文档标题
                .title("SpringBoot-Swagger2-Mybatis-Demo")
                // 接口文档描述
                .description("API Description: 用于敏捷开发 >>> Spring Boot 测试使用 Swagger2 构建RESTful API")
                // 服务条款网址
                .termsOfServiceUrl("http://localhost/")
                // 接口文档维护联系人（创建人）
                .contact(new Contact("Drew", "http://www.baidu.com", "1234567890@qq.com"))
                // 接口文档的版本号
                .version("1.0.0")
                .build();
    }
}
