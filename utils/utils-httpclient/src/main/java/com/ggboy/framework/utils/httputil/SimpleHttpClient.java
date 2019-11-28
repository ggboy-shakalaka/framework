package com.ggboy.framework.utils.httputil;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class SimpleHttpClient extends SimpleHttpClientAbstract<String> {

    public final static SimpleHttpClient defaultClient = new SimpleHttpClient(HttpClientFactory.defaultHttpClient());

    public SimpleHttpClient(HttpClient httpClient) {
        super(httpClient);
    }

    @Override
    boolean beforeRequest(HttpRequestBase request) {
        return true;
    }

    @Override
    String afterRequest(HttpResponse response) throws IOException {
        return response == null ? null : EntityUtils.toString(response.getEntity());
    }
}