package demo.httpclient;

import com.ggboy.framework.common.constant.SystemConstant;
import com.ggboy.framework.utils.httputil.HttpClientFactory;
import com.ggboy.framework.utils.httputil.StringSimpleHttp;

import java.io.IOException;

public class HttpDemo {
    public static void main(String[] args) throws IOException {
        String responseStr = StringSimpleHttp.startDefaultRequest("http://baidu.com")
                .addUrlParams("a", "1")
                .addUrlParams("b", "2")
                .addHeader("c", "3")
                .addHeader("d", "4")
                .doPost(StringSimpleHttp.buildJsonEntity("{'e':'5'}"));
        System.out.println(responseStr);
    }
}