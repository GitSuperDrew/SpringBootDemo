package com.study.module.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Administrator
 */
@SpringBootApplication
public class JpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaApplication.class, args);
        System.out.println(" ▄▄▄██▀▀▀██▓███   ▄▄▄      \n" +
                "   ▒██  ▓██░  ██▒▒████▄    \n" +
                "   ░██  ▓██░ ██▓▒▒██  ▀█▄  \n" +
                "▓██▄██▓ ▒██▄█▓▒ ▒░██▄▄▄▄██ \n" +
                " ▓███▒  ▒██▒ ░  ░ ▓█   ▓██▒\n" +
                " ▒▓▒▒░  ▒▓▒░ ░  ░ ▒▒   ▓▒█░\n" +
                " ▒ ░▒░  ░▒ ░       ▒   ▒▒ ░\n" +
                " ░ ░ ░  ░░         ░   ▒   \n" +
                " ░   ░                 ░  ░\n" +
                "                           ");
    }

}
