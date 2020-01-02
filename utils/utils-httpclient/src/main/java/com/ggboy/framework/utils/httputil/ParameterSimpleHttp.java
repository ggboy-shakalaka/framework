package com.ggboy.framework.utils.httputil;

import com.ggboy.framework.common.constant.SystemConstant;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ParameterSimpleHttp extends SimpleHttpAbstract<String> {
    private final static ParameterSimpleHttp defaultClient = new ParameterSimpleHttp();

    public static Request startDefaultRequest(String url) {
        return defaultClient.startRequest(url);
    }

    @Override
    boolean beforeRequest(HttpRequestBase request) {
        return true;
    }

    @Override
    String afterRequest(HttpResponse response) throws IOException {
        return response == null ? null : EntityUtils.toString(response.getEntity());
    }

    public static HttpEntity buildJsonEntity(String json) {
        return new StringEntity(json, ContentType.APPLICATION_JSON.withCharset(SystemConstant.system_charset));
    }

    public static class ParameterBuilder {
        private List<BasicNameValuePair> params = new LinkedList<>();

        public ParameterBuilder add(String name, String value) {
            params.add(new BasicNameValuePair(name, value));
            return this;
        }

        public ParameterBuilder add(Map<String, String> map) {
            if (map == null)
                return this;

            for (Map.Entry<String, String> entry : map.entrySet())
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            return this;
        }

        public List<BasicNameValuePair> build() {
            return params;
        }
    }
}