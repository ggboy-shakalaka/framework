package cn.zhaizq.sso.web.controller;

import cn.zhaizq.sso.sdk.SsoFilter;
import cn.zhaizq.sso.sdk.SsoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/login.html")
    public String loginHtml() {
        return "view/login.html";
    }

    @GetMapping(value = "/{appId}/refresh")
    public void token(@PathVariable String appId, @RequestParam String redirect) throws IOException {
        Cookie ssoToken = SsoHelper.getSsoToken(request);

        if (ssoToken == null)
            response.sendRedirect("/" + appId + "/login.html?redirect=" + redirect);
        else
            response.sendRedirect(redirect + "?" + SsoFilter.Conf.TOKEN_NAME + "=" + ssoToken.getValue());
    }
}