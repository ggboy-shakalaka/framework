package cn.zhaizq.sso.sdk.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SsoLoginRequest {
    private String name;
    private String password;
    private String code;
}