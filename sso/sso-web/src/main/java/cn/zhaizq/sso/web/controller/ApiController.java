package cn.zhaizq.sso.web.controller;

import cn.zhaizq.sso.sdk.SsoFilter;
import cn.zhaizq.sso.sdk.SsoHelper;
import cn.zhaizq.sso.sdk.domain.SsoUser;
import cn.zhaizq.sso.sdk.domain.request.QueryConfig;
import cn.zhaizq.sso.sdk.domain.request.SsoCheckToken;
import cn.zhaizq.sso.sdk.domain.response.SsoCheckResult;
import cn.zhaizq.sso.sdk.domain.response.SsoConfig;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/api")
public class ApiController {
    @PostMapping("/query_config")
    public SsoConfig query_config(@RequestBody QueryConfig queryConfig) {
        SsoConfig config = new SsoConfig();
        config.setServerPath("http://sso.zhaizq.cn");
        config.setLoginPath("http://sso.zhaizq.cn/login");
        config.setLogoutPath("http://sso.zhaizq.cn/logout");
        config.setTokenPath("http://sso.zhaizq.cn/api/token");
        return config;
    }

    @PostMapping("/check_token")
    public SsoCheckResult check_token(@RequestBody SsoCheckToken ssoCheckToken) {
        SsoCheckResult ssoCheckResult = new SsoCheckResult();

        if (ssoCheckToken.getToken().equals("asdfg")) {
            ssoCheckResult.setStatus(SsoCheckResult.Status._3);
            return ssoCheckResult;
        }

        ssoCheckResult.setStatus(SsoCheckResult.Status._1);
        SsoUser ssoUser = new SsoUser();
        ssoUser.setId("999");
        ssoUser.setName("zhaizq");
        ssoCheckResult.setSsoUser(ssoUser);
        return ssoCheckResult;
    }

    @GetMapping("/token")
    public void token(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = SsoHelper.getSsoToken(request);
        if (token == null) {
            token = "zhaizq.token";
            Cookie cookie = new Cookie(SsoFilter.Conf.TOKEN_NAME, token);
            response.addCookie(cookie);
        }
        response.sendRedirect(new URIBuilder(URI.create(request.getParameter("source"))).addParameter(SsoFilter.Conf.TOKEN_NAME, token).toString());
    }
}