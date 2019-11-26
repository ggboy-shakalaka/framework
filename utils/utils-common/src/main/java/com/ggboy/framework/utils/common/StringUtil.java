package com.ggboy.framework.utils.common;

public class StringUtil {
    public final static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public final static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public final static String toString(Object... objs) {
        return toString(null, objs);
    }

    public final static String toString(String delimiter, Object... objs) {
        if (objs == null || objs.length == 0)
            return "";

        StringBuilder sb = new StringBuilder();
        for (Object obj : objs) {
            if (delimiter != null)
                sb.append(delimiter);

            if (obj == null)
                continue;

            sb.append(obj.toString());
        }

        return sb.substring(delimiter == null ? 0 : delimiter.length());
    }
}