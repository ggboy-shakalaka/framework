package cn.zhaizq.sso.sdk;

import cn.zhaizq.sso.sdk.domain.SsoConfig;
import cn.zhaizq.sso.sdk.domain.request.SsoCheckTokenRequest;
import cn.zhaizq.sso.sdk.domain.request.SsoLoginRequest;
import cn.zhaizq.sso.sdk.domain.request.SsoLogoutRequest;
import cn.zhaizq.sso.sdk.domain.response.SsoCheckTokenResponse;
import cn.zhaizq.sso.sdk.domain.response.SsoLoginResponse;
import cn.zhaizq.sso.sdk.domain.response.SsoLogoutResponse;
import cn.zhaizq.sso.sdk.domain.response.SsoQueryConfigResponse;
import com.alibaba.fastjson.JSON;
import com.ggboy.framework.common.exception.InternalException;

import java.io.IOException;

public class SsoApi {
    private String server;
    private String appId;
    private SsoConfig ssoConfig;

    public SsoApi(String server, String appId) {
        this.server = server;
        this.appId = appId;
    }

    private SsoConfig initSsoConfig() throws InternalException {
        try {
            SsoQueryConfigResponse response = queryConfig();

            if ("200".equalsIgnoreCase(response.getCode()))
                return response.getData();

            throw new InternalException("单点登录服务初始化失败, message: " + response.getMessage());
        } catch (Exception e) {
            throw new InternalException("单点登录服务初始化失败", e);
        }
    }

    private SsoQueryConfigResponse queryConfig() throws IOException {
        String requestPath = server + "/api/" + appId + "/query_config";
        String response = SsoHelper.doJsonRequest(requestPath, null);
        return JSON.parseObject(response, SsoQueryConfigResponse.class);
    }

    public SsoCheckTokenResponse checkToken(String token) throws IOException {
        String requestPath = server + getSsoConfig().getCheckTokenPath();
        SsoCheckTokenRequest request = new SsoCheckTokenRequest();
        request.setToken(token);
        String response = SsoHelper.doJsonRequest(requestPath, JSON.toJSONString(request));
        return JSON.parseObject(response, SsoCheckTokenResponse.class);
    }

    public SsoLoginResponse login(SsoLoginRequest request) throws IOException {
        String requestPath = server + getSsoConfig().getLoginPath();
        String response = SsoHelper.doJsonRequest(requestPath, JSON.toJSONString(request));
        return JSON.parseObject(response, SsoLoginResponse.class);
    }

    public SsoLogoutResponse logout(String token) throws IOException {
        String requestPath = server + getSsoConfig().getLogoutPath();
        SsoLogoutRequest request = new SsoLogoutRequest();
        request.setSsoToken(token);
        String response = SsoHelper.doJsonRequest(requestPath, JSON.toJSONString(request));
        return JSON.parseObject(response, SsoLogoutResponse.class);
    }

    public SsoConfig getSsoConfig() {
        if (ssoConfig == null)
            return ssoConfig = initSsoConfig();
        return ssoConfig;
    }
}