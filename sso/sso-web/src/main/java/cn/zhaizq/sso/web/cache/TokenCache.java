package cn.zhaizq.sso.web.cache;

import cn.zhaizq.sso.sdk.domain.SsoUser;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TokenCache {
    private Map<String, String> cache = new HashMap<>();

    public String get(String name) {
        return cache.get(name);
    }

    public String put(String name, String token) {
        return cache.put(name, token);
    }
}