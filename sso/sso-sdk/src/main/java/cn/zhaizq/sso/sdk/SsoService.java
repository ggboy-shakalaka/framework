package cn.zhaizq.sso.sdk;

import cn.zhaizq.sso.sdk.domain.SsoConfig;
import com.ggboy.framework.utils.httputil.StringSimpleHttp;
import org.apache.http.entity.SerializableEntity;

import java.io.IOException;

public class SsoService {
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
        serverPath = StringSimpleHttp.startDefaultRequest(server + "/api/config/serverPath").addUrlParams("appId", appId).doGet();
        loginPath = StringSimpleHttp.startDefaultRequest(server + "/api/config/loginPath").addUrlParams("appId", appId).doGet();
        logoutPath = StringSimpleHttp.startDefaultRequest(server + "/api/config/logoutPath").addUrlParams("appId", appId).doGet();
    }

    public static void main(String[] args) throws IOException {
        SsoConfig ssoConfig = new SsoConfig();
//        ssoConfig.serverPath = "haha";
        String s = StringSimpleHttp.startDefaultRequest("http://localhost:8080/api/auth/test2").doPost(new SerializableEntity(ssoConfig));
        System.out.println(s);
    }
}