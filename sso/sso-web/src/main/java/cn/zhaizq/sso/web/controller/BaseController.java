package cn.zhaizq.sso.web.controller;

import cn.zhaizq.sso.sdk.SsoFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class BaseController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public void login(@RequestParam("name") String name) throws IOException {
        if (!"zhaizq".equals(name)) {
            response.getOutputStream().print("login fail!");
            return;
        }

        String source = "http://localhost/haha";

        new Cookie(SsoFilter.Conf.TOKEN_NAME, "12345");
        response.addCookie(new Cookie(SsoFilter.Conf.TOKEN_NAME, "12345"));
        response.sendRedirect(source);
    }

    @RequestMapping("/test")
    public String test() {
        return "index.html";
    }
}