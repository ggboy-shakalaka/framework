package com.zhaizq.framework.spring.cloud.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.server.EurekaController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class BaseController {
    @Autowired
    private EurekaController eurekaController;

    @RequestMapping("/switch")
    public String action(HttpServletRequest request, Map<String, Object> model) {
        eurekaController.status(request, model);
        return "eureka/switch";
    }
}