package cn.zhaizq.sso.sdk;

import com.ggboy.framework.utils.httputil.SimpleHttpClient;

import java.io.IOException;

public class SsoService {
    public final static SimpleHttpClient client = SimpleHttpClient.defaultClient;
    public final static long timeout = 1000L * 60 * 10;

    public String server;
    public String appId;

    public long time;

    public String serverPath;
    public String loginPath;
    public String logoutPath;

    public SsoService(String server, String appId) {
        this.server = server;
        this.appId = appId;
        try {
            flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getServerPath() {
        return serverPath;
    }

    public String getLoginPath() {
        return loginPath;
    }

    public String getLogoutPath() {
        return logoutPath;
    }

    private boolean isTimeout() {
        return System.currentTimeMillis() - time > timeout;
    }

    private void flush() throws IOException {
        // todo 超时判断 并发处理
        doFlush();
    }

    private void doFlush() throws IOException {
        serverPath = client.startRequest(server + "/api/config/serverPath").addUrlParams("appId", appId).doGet();
        loginPath = client.startRequest(server + "/api/config/loginPath").addUrlParams("appId", appId).doGet();
        logoutPath = client.startRequest(server + "/api/config/logoutPath").addUrlParams("appId", appId).doGet();
    }
}