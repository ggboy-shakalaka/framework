package httpclient;

import com.ggboy.framework.common.constant.SystemConstant;
import com.ggboy.framework.utils.httputil.HttpClientFactory;
import com.ggboy.framework.utils.httputil.SimpleHttpClient;

import java.io.IOException;

public class HttpDemo {
    public static void main(String[] args) throws IOException {
        SimpleHttpClient httpClient = new SimpleHttpClient(HttpClientFactory.defaultHttpClient());
        String responseStr = httpClient.startRequest("http://baidu.com", SystemConstant.system_charset)
                .addUrlParams("a", "1")
                .addUrlParams("b", "2")
                .addHeader("c", "3")
                .addHeader("d", "4")
                .postJson("{'e':'5'}");
        System.out.println(responseStr);
    }
}