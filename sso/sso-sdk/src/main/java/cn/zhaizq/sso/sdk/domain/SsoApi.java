package cn.zhaizq.sso.sdk.domain;

import com.alibaba.fastjson.JSON;
import com.ggboy.framework.utils.httputil.StringSimpleHttp;
import org.apache.http.HttpEntity;

import java.io.IOException;

public enum SsoApi {
    query_config("/api/test1") {
        public String doRequest(Object SsoApi) throws IOException {
            return JSON.parseObject(super.doRequest(SsoApi), String.class);
        }
    },
    ;

    private String path;

    SsoApi(String path) {
        this.path = path;
    }

    public String doRequest(Object data) throws IOException {
        System.out.println("http://localhost:8080" + path);
        HttpEntity httpEntity = StringSimpleHttp.buildJsonEntity(JSON.toJSONString(data));
        return StringSimpleHttp.startDefaultRequest("http://localhost:8080" + path).doPost(httpEntity);
    }

    protected <T> T action() {
        return (T) null;
    }

    public static void main(String[] args) {
    }
}