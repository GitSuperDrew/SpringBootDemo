package com.study.module.springbootmybatis.config;

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
