package com.ggboy.framework.utils.httputil;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public abstract class SimpleHttpAbstract<T> {
    private final HttpClient httpClient;

    protected SimpleHttpAbstract() {
        this(HttpClientFactory.defaultHttpClient());
    }

    protected SimpleHttpAbstract(HttpClient httpClient) {
        if (httpClient == null)
            throw new IllegalArgumentException("HttpClient can not be null");
        this.httpClient = httpClient;
    }

    abstract boolean beforeRequest(HttpRequestBase request);

    abstract T afterRequest(HttpResponse response) throws IOException;

    public Request startRequest(String url) {
        return new Request(url);
    }

    public class Request {
        private URIBuilder uriBuilder;
        private List<Header> headers;

        Request(String url) {
            this.uriBuilder = new URIBuilder(URI.create(url));
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

        public T doPost(HttpEntity entity) throws IOException {
            HttpPost httpPost = new HttpPost();
            httpPost.setURI(URI.create(uriBuilder.toString()));
            httpPost.setHeaders(headers == null ? null : headers.toArray(new Header[0]));
            httpPost.setEntity(entity);
            return request(httpPost);
        }

        public T doGet() throws IOException {
            HttpGet httpGet = new HttpGet();
            httpGet.setURI(URI.create(uriBuilder.toString()));
            httpGet.setHeaders(headers == null ? null : headers.toArray(new Header[0]));
            return request(httpGet);
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