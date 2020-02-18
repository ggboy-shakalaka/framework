package cn.zhaizq.sso.web.controller.abc;

import cn.zhaizq.sso.sdk.SsoConstant;
import cn.zhaizq.sso.sdk.SsoHelper;
import cn.zhaizq.sso.sdk.domain.SsoConfig;
import cn.zhaizq.sso.sdk.domain.SsoUser;
import cn.zhaizq.sso.sdk.domain.request.SsoCheckTokenRequest;
import cn.zhaizq.sso.sdk.domain.request.SsoLoginRequest;
import cn.zhaizq.sso.sdk.domain.request.SsoLogoutRequest;
import cn.zhaizq.sso.sdk.domain.response.SsoCheckTokenResponse;
import cn.zhaizq.sso.sdk.domain.response.SsoLoginResponse;
import cn.zhaizq.sso.sdk.domain.response.SsoLogoutResponse;
import cn.zhaizq.sso.sdk.domain.response.SsoQueryConfigResponse;
import cn.zhaizq.sso.web.cache.TokenCache;
import cn.zhaizq.sso.web.cache.UserCache;
import cn.zhaizq.sso.web.controller.BaseController;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/{appId}")
public class ApiController extends BaseController {
    @Autowired
    private UserCache userCache;
    @Autowired
    private TokenCache tokenCache;

    @Value("${sso.base.location}")
    private String baseLocation;

    @PostMapping("/query_config")
    public SsoQueryConfigResponse query_config(@PathVariable String appId) {
        SsoConfig config = new SsoConfig();
        config.setServerPath(baseLocation);
        config.setLoginPath(String.format("/api/%s/login", appId));
        config.setLogoutPath(String.format("/api/%s/logout", appId));
        config.setCheckTokenPath(String.format("/api/%s/check_token", appId));
        config.setRefreshTokenPath(String.format("/api/%s/refresh_token", appId));

        SsoQueryConfigResponse response = new SsoQueryConfigResponse();
        response.setCode("200");
        response.setData(config);
        return response;
    }


    @PostMapping("/check_token")
    public SsoCheckTokenResponse check_token(@PathVariable String appId, @RequestBody SsoCheckTokenRequest ssoCheckToken) {
        SsoCheckTokenResponse response = new SsoCheckTokenResponse();

        SsoUser ssoUser = userCache.get(ssoCheckToken.getToken());
        if (ssoUser == null) {
            response.setCode("400");
            return response;
        }

        response.setCode("200");
        response.setData(ssoUser);
        return response;
    }

    @GetMapping("/refresh_token")
    public void refresh_token(@PathVariable String appId, @RequestParam String redirect, @RequestParam String login_url) throws IOException {
        Cookie tokenCookie = SsoHelper.getSsoToken(request);

        if (tokenCookie != null && tokenCookie.getValue() != null && userCache.get(tokenCookie.getValue()) != null) {
            URIBuilder uri = new URIBuilder(URI.create(redirect));
            uri.addParameter(SsoConstant.TOKEN_NAME, tokenCookie.getValue());
            response.sendRedirect(uri.toString());
            return;
        }

        if (login_url != null && login_url.length() > 0) {
            response.sendRedirect(login_url);
            return;
        }

        URIBuilder uri = new URIBuilder(URI.create("/api/login.html"));
        uri.addParameter(SsoConstant.REDIRECT, redirect);
        uri.addParameter("appId", appId);
        response.sendRedirect(uri.toString());
    }

    @PostMapping("/login")
    public SsoLoginResponse login(@PathVariable String appId, @RequestBody SsoLoginRequest loginRequest) {
        SsoLoginResponse response = new SsoLoginResponse();
        if (!"zhaizq".equals(loginRequest.getName()) && !"1".equals(loginRequest.getPassword())) {
            response.setCode("400");
            return response;
        }

        String ssoToken = tokenCache.get(loginRequest.getName());
        if (ssoToken == null) {
            ssoToken = UUID.randomUUID().toString();
            tokenCache.put(loginRequest.getName(), ssoToken);
        }

        userCache.put(ssoToken, new SsoUser());
        response.setCode("200");
        response.setData(ssoToken);
        return response;
    }

    @PostMapping("/logout")
    public SsoLogoutResponse logout(@RequestBody SsoLogoutRequest request) {
        userCache.put(request.getSsoToken(), null);
        return new SsoLogoutResponse();
    }
}