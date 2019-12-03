package com.ggboy.framework.utils.common;

import java.util.UUID;

public class UuidUtil {
    public final static String random() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }
}