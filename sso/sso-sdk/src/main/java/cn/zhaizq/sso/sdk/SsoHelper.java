package cn.zhaizq.sso.sdk;

public class SsoHelper {
    public static boolean isMatch(String pattern, String path) {
        if (path == null || pattern == null)
            return false;

        path = path.trim();
        pattern = pattern.trim();

        return path.equals(pattern) || path.startsWith(pattern + "/");
    }
}