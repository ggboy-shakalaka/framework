package cn.zhaizq.sso.sdk;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class SsoHelper {
    public static boolean isMatch(String pattern, String path) {
        if (path == null || pattern == null)
            return false;

        path = path.trim();
        pattern = pattern.trim();

        return path.equals(pattern) || path.startsWith(pattern + "/");
    }

    public static String getSsoToken(HttpServletRequest request) {
        if (request.getCookies() == null)
            return null;

        for (Cookie cookie : request.getCookies()) {
            if (SsoFilter.Conf.TOKEN_NAME.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }
}