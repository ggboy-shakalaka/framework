package cn.zhaizq.sso.sdk.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

@Getter
@Setter
public class SsoConfig implements Serializable {
    private String serverPath;
    private String loginPath;
    private String logoutPath;
}