package com.zhaizq.framework.demo.eurekaClient.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("eureka-client")
public interface BaseFeign {
    @RequestMapping("hello")
    String hello();
}