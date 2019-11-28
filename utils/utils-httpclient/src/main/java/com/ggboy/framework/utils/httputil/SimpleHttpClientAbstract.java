package com.ggboy.framework.utils.httputil;

import com.ggboy.framework.common.constant.SystemConstant;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public abstract class SimpleHttpClientAbstract<T> {
    private final HttpClient httpClient;

    protected SimpleHttpClientAbstract(HttpClient httpClient) {
        if (httpClient == null)
            throw new RuntimeException("HttpClient can not be null");
        this.httpClient = httpClient;
    }

    abstract boolean beforeRequest(HttpRequestBase request);

    abstract T afterRequest(HttpResponse response) throws IOException;

    public Request startRequest(String url, Charset charset) {
        return new Request(url, charset);
    }

    public Request startRequest(String url) {
        return startRequest(url, null);
    }

    public class Request {
        private Charset charset;
        private URIBuilder uriBuilder;
        private List<Header> headers;

        Request(String url, Charset charset) {
            this.uriBuilder = new URIBuilder(URI.create(url));
            this.charset = charset == null ? SystemConstant.system_charset : charset;
        }

        public Request addUrlParams(String name, String value) {
            uriBuilder.addParameter(name, value);
            return this;
        }

        public Request addHeader(String name, String value) {
            headers = headers == null ? new ArrayList<>() : headers;
            headers.add(new BasicHeader(name, value));
            return this;
        }

        public T postJson(String json) throws IOException {
            return doPost(new StringEntity(json, ContentType.APPLICATION_JSON.withCharset(charset)));
        }

        public T postXml(String xml) throws IOException {
            return doPost(new StringEntity(xml, ContentType.APPLICATION_XML.withCharset(charset)));
        }

        public T doPost(HttpEntity entity) throws IOException {
            HttpPost httpPost = new HttpPost();
            httpPost.setURI(URI.create(uriBuilder.toString()));
            httpPost.setHeaders(headers == null ? null : headers.toArray(new Header[0]));
            httpPost.setEntity(entity);
            return request(httpPost);
        }

        private T request(HttpRequestBase request) throws IOException {
            HttpResponse response = beforeRequest(request) ? doRequest(request) : null;
            return response == null ? null : afterRequest(response);
        }

        private HttpResponse doRequest(HttpRequestBase request) throws IOException {
            return httpClient.execute(request);
        }
    }
}