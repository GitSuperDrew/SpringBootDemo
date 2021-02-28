package edu.study.module.undertow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试 undertow 服务器，并移除 springboot默认的服务器
 *
 * @author drew
 */
@RestController
@SpringBootApplication
public class UndertowApplication {

    public static void main(String[] args) {
        SpringApplication.run(UndertowApplication.class, args);
    }

    @GetMapping(path = "/undertow/test")
    public String testUndertowServer() {
        return "use Undertow server success!";
    }

}
