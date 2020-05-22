package com.example.servicehi.controller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author Administrator
 * @date 2020/5/22 下午 8:06
 */
@RestController
public class HiController {
    Logger logger = LoggerFactory.getLogger(HiController.class);
    @Value("${server.port}")
    String port;

    @RequestMapping("/hi")
    public String home() {
        return "HELLO: " + ", I am from port : " + port;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping("/hello")
    public String hello() {
        return "hello you";
    }

    public OAuth2Authentication getPrinciple(OAuth2Authentication auth2Authentication, Principal principal,
                                             Authentication authentication) {
        logger.info(auth2Authentication.getUserAuthentication().getAuthorities().toString());
        logger.info(auth2Authentication.toString());
        logger.info("principal.toString()  ===>  " + principal.toString());
        logger.info("principal.getName()  ===> " + principal.getName());
        return auth2Authentication;
    }

}
