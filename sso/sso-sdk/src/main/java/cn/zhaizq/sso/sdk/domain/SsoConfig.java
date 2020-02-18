package cn.zhaizq.sso.sdk.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SsoConfig {
    private String serverPath;
    private String loginPath;
    private String logoutPath;
    private String refreshTokenPath;
    private String checkTokenPath;
}