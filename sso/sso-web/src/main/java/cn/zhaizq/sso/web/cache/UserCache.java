package cn.zhaizq.sso.web.cache;

import cn.zhaizq.sso.sdk.domain.SsoUser;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserCache {
    private Map<String, SsoUser> cache = new HashMap<>();

    public SsoUser get(String token) {
        return token == null ? null : cache.get(token);
    }

    public SsoUser put(String token, SsoUser ssoUser) {
        return cache.put(token, ssoUser);
    }
}