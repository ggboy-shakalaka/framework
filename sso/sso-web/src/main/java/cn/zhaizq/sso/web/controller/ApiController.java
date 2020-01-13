package cn.zhaizq.sso.web.controller;

import cn.zhaizq.sso.sdk.SsoFilter;
import cn.zhaizq.sso.sdk.domain.SsoUser;
import cn.zhaizq.sso.sdk.domain.request.QueryConfig;
import cn.zhaizq.sso.sdk.domain.request.SsoCheckToken;
import cn.zhaizq.sso.sdk.domain.response.SsoCheckResult;
import cn.zhaizq.sso.sdk.domain.response.SsoConfig;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    private Map<String, SsoUser> userCache = new HashMap<>();

    @PostMapping("/query_config")
    public SsoConfig query_config(@RequestBody QueryConfig queryConfig) {
        SsoConfig config = new SsoConfig();
        config.setServerPath("http://sso.zhaizq.cn");
        config.setLoginPath("http://sso.zhaizq.cn/" + queryConfig.getAppId() + "/login.html");
        config.setLogoutPath("http://sso.zhaizq.cn/" + queryConfig.getAppId() + "/logout.html");
        config.setTokenPath("http://sso.zhaizq.cn/" + queryConfig.getAppId() + "/refresh");
        return config;
    }

    @PostMapping("/check_token")
    public SsoCheckResult check_token(@RequestBody SsoCheckToken ssoCheckToken) {
        SsoCheckResult ssoCheckResult = new SsoCheckResult();

        SsoUser ssoUser = userCache.get(ssoCheckToken.getToken());
        if (ssoUser != null) {
            ssoCheckResult.setStatus(SsoCheckResult.Status._1);
            ssoCheckResult.setSsoUser(ssoUser);
            return ssoCheckResult;
        }

        ssoCheckResult.setStatus(SsoCheckResult.Status._3);
        return ssoCheckResult;
    }

    @PostMapping("/doLogin")
    public String doLogin(@RequestBody LoginRequest loginRequest) {
        if ("zhaizq".equals(loginRequest.getName()) && "1".equals(loginRequest.getPassword())) {
            String ssoToken = UUID.randomUUID().toString();
            SsoUser ssoUser = new SsoUser();
            ssoUser.setId("999");
            ssoUser.setName(loginRequest.getName());
            userCache.clear();
            userCache.put(ssoToken, ssoUser);
            response.addCookie(new Cookie("sso.token", ssoToken));
            return "{\"code\":\"OK\"}";
        }

        return "{\"code\":\"FAIL\"}";
    }
}

@Getter
@Setter
class LoginRequest {
    private String name;
    private String password;
    private String code;
}