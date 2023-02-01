package com.zhaizq.framework.spring.boot.admin.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class AdminClientApplication {
    public static void main(String[] args) {
        System.setProperty("application.start.uuid", UUID.randomUUID().toString());
        SpringApplication.run(AdminClientApplication.class, "--spring.profiles.active=client");
    }
}