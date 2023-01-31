package com.zhaizq.framework.demo.springbootAdmin.server;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * https://consolelog.gitee.io/docs-spring-boot-admin-docs-chinese
 */
@EnableAdminServer
@SpringBootApplication
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, "--spring.profiles.active=server");
    }
}