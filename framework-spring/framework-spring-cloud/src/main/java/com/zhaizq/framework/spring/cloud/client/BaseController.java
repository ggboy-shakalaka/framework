package com.zhaizq.framework.spring.cloud.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {
    @Value("${server.port}")
    private Integer port;
    @Autowired
    private BaseFeign baseFeign;

    @RequestMapping
    public String action() {
        return baseFeign.hello();
    }

    @RequestMapping("hello")
    public String hello() {
        return "Hello! Server port: " + port;
    }
}