package cn.zhaizq.sso.sdk;

import cn.zhaizq.sso.sdk.domain.SsoConfig;
import com.alibaba.fastjson.JSON;
import com.ggboy.framework.utils.httputil.StringSimpleHttp;
import org.apache.http.HttpEntity;

import java.io.IOException;

public class SsoApi {
    private String ssoConfig;
    private String doRequest(Object request) throws IOException {
        HttpEntity httpEntity = StringSimpleHttp.buildJsonEntity(JSON.toJSONString(request));
        return StringSimpleHttp.startDefaultRequest("ssoConfig.get").doPost(httpEntity);
    }
}