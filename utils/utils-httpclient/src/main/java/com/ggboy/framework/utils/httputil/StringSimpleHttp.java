package com.ggboy.framework.utils.httputil;

import com.ggboy.framework.common.constant.SystemConstant;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class StringSimpleHttp extends SimpleHttpAbstract<String> {
    private final static StringSimpleHttp defaultClient = new StringSimpleHttp();

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

    public static HttpEntity buildXmlEntity(String xml) {
        return new StringEntity(xml, ContentType.APPLICATION_XML.withCharset(SystemConstant.system_charset));
    }
}