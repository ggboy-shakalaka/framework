package cn.zhaizq.sso.sdk.domain.response;

import lombok.Getter;
import lombok.Setter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

@Getter
@Setter
public class SsoConfig {
    private String serverPath;
    private String loginPath;
    private String logoutPath;
    private String tokenPath;
}