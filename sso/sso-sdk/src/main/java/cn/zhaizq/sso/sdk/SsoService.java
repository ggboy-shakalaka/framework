package cn.zhaizq.sso.sdk;

import cn.zhaizq.sso.sdk.domain.request.SsoLoginRequest;
import cn.zhaizq.sso.sdk.domain.request.SsoQueryConfigRequest;
import cn.zhaizq.sso.sdk.domain.request.SsoCheckTokenRequest;
import cn.zhaizq.sso.sdk.domain.response.SsoLoginResponse;
import cn.zhaizq.sso.sdk.domain.response.SsoCheckTokenResponse;
import cn.zhaizq.sso.sdk.domain.response.SsoLogoutResponse;
import cn.zhaizq.sso.sdk.domain.response.SsoQueryConfigResponse;
import org.apache.http.client.utils.URIBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;

public class SsoService {
    public SsoApi ssoApi;

    public SsoService(String server, String appId) {
        this.ssoApi = new SsoApi(server, appId);
    }

    public String getRefreshTokenPath(String redirect, String login_url) {
        String path = ssoApi.getSsoConfig().getServerPath() + ssoApi.getSsoConfig().getRefreshTokenPath();
        URIBuilder uri = new URIBuilder(URI.create(path));
        uri.addParameter(SsoConstant.REDIRECT, redirect);
        uri.addParameter(SsoConstant.LOGIN_URL, login_url);
        return uri.toString();
    }

    public SsoCheckTokenResponse checkToken(String token) throws IOException {
        SsoCheckTokenResponse response = new SsoCheckTokenResponse();
        if (token == null) {
            response.setCode("400");
            return response;
        }

        return ssoApi.checkToken(token);
    }

    public SsoLoginResponse login(String name, String pwd) throws IOException {
        SsoLoginRequest req = new SsoLoginRequest();
        req.setName(name);
        req.setPassword(pwd);
        return ssoApi.login(req);
    }

    public SsoLogoutResponse logout(String token) throws IOException {
        return ssoApi.logout(token);
    }
}