package cn.zhaizq.sso.sdk.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SsoLogoutRequest {
    private String ssoToken;
}