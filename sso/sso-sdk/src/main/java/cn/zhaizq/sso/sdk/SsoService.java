package cn.zhaizq.sso.sdk;

import cn.zhaizq.sso.sdk.domain.request.QueryConfig;
import cn.zhaizq.sso.sdk.domain.request.SsoCheckToken;
import cn.zhaizq.sso.sdk.domain.response.SsoCheckResult;
import cn.zhaizq.sso.sdk.domain.response.SsoConfig;

import java.io.IOException;

public class SsoService {
    public final static long timeout = 1000L * 60 * 10;

    public String server;
    public String appId;
    public SsoApi ssoApi;

    public SsoService(String server, String appId) {
        this.server = server;
        this.appId = appId;
        ssoApi = new SsoApi(server);
    }

    public String getServerPath() throws IOException {
        return getSsoConfig().getServerPath();
    }

    public String getLoginPath() throws IOException {
        return getSsoConfig().getLoginPath();
    }

    public String getLogoutPath() throws IOException {
        return getSsoConfig().getLogoutPath();
    }

    public String getTokenPath() throws IOException {
        return getSsoConfig().getTokenPath();
    }

    public SsoCheckResult checkToken(String token) throws IOException {
        if (token == null || token.length() == 0) {
            SsoCheckResult ssoCheckResult = new SsoCheckResult();
            ssoCheckResult.setStatus(SsoCheckResult.Status._UNKNOWN);
            return ssoCheckResult;
        }

        SsoCheckToken checkToken = new SsoCheckToken();
        checkToken.setAppId(appId);
        checkToken.setToken(token);
        return ssoApi.checkToken(checkToken);
    }

    public SsoConfig getSsoConfig() throws IOException {
        QueryConfig queryConfig = new QueryConfig();
        queryConfig.setAppId(appId);
        return ssoApi.queryConfig(queryConfig);
    }
}