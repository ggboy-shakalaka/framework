package com.zhaizq.framework.demo.springbootAdmin.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class ClientApplication {
    public static void main(String[] args) {
        System.setProperty("application.start.uuid", UUID.randomUUID().toString());
        SpringApplication.run(ClientApplication.class, "--spring.profiles.active=client");
    }
}