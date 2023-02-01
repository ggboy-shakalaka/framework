package com.zhaizq.framework.spring.cloud.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("eureka-client")
public interface BaseFeign {
    @RequestMapping("hello")
    String hello();
}