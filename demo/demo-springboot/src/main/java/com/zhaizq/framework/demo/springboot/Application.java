package com.zhaizq.framework.demo.springboot;

import com.zhaizq.framework.demo.springboot.basic.FirstApplication;
import com.zhaizq.framework.demo.springboot.paramsResolver.SecondApplication;
import org.springframework.boot.SpringApplication;

public class Application {
    public static void main(String... args) {
        new SpringApplication(FirstApplication.class).run("--server.port=8001");
        new SpringApplication(SecondApplication.class).run("--server.port=8002");
    }
}