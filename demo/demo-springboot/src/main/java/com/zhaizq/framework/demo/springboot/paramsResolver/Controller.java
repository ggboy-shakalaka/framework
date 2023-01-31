package com.zhaizq.framework.demo.springboot.paramsResolver;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @RequestMapping("demo1")
    public String demo1(@SingleParamDemo.SingleParam String str) {
        return str;
    }
}