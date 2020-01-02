package cn.zhaizq.sso.web.controller;

import cn.zhaizq.sso.sdk.domain.SsoConfig;
import cn.zhaizq.sso.sdk.domain.SsoUser;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.ObjectInputStream;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @PostMapping("/login")
    public String action() {
        return "OK";
    }

    @GetMapping("/test")
    public Object test(SsoConfig config) {

        return config;
    }

    @Autowired
    private HttpServletRequest request;

    @PostMapping("/test2")
    public Object test2(SsoConfig config) throws IOException, ClassNotFoundException {
        System.out.println(JSON.toJSONString(config));
        ObjectInputStream objectInputStream = new ObjectInputStream(request.getInputStream());
        Object o = objectInputStream.readObject();
        return o;
    }
}