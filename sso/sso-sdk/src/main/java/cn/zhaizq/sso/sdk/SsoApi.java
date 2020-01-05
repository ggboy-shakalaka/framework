package cn.zhaizq.sso.sdk;

import cn.zhaizq.sso.sdk.domain.SsoApiEnum;
import cn.zhaizq.sso.sdk.domain.SsoUser;
import cn.zhaizq.sso.sdk.domain.request.QueryConfig;
import cn.zhaizq.sso.sdk.domain.request.SsoCheckToken;
import cn.zhaizq.sso.sdk.domain.response.SsoCheckResult;
import cn.zhaizq.sso.sdk.domain.response.SsoConfig;
import com.alibaba.fastjson.JSON;
import com.ggboy.framework.utils.httputil.StringSimpleHttp;

import java.io.IOException;

public class SsoApi {
    private String server;

    public SsoApi(String server) {
        this.server = server;
    }

    public SsoConfig queryConfig(QueryConfig query) throws IOException {
        String response = doRequest(SsoApiEnum.query_config, JSON.toJSONString(query));
        return JSON.parseObject(response, SsoConfig.class);
    }

    public SsoCheckResult checkToken(SsoCheckToken query) throws IOException {
        String response = doRequest(SsoApiEnum.check_token, JSON.toJSONString(query));
        return JSON.parseObject(response, SsoCheckResult.class);
    }

    private String doRequest(SsoApiEnum apiEnum, String request) throws IOException {
        return StringSimpleHttp.startDefaultRequest(apiEnum.getUrl(server)).doPost(StringSimpleHttp.buildJsonEntity(request));
    }
}