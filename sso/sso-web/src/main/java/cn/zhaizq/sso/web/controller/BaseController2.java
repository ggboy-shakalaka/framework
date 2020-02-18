package cn.zhaizq.sso.web.controller;

import cn.zhaizq.sso.sdk.SsoHelper;
import cn.zhaizq.sso.sdk.SsoService;
import cn.zhaizq.sso.sdk.domain.response.SsoLoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class BaseController2 {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "index";
    }

    @GetMapping("/api/login.html")
    public String loginHtml() {
        return "/view/login.html";
    }
}