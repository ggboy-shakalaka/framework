package cn.zhaizq.sso.sdk.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SsoCheckToken {
    private String appId;
    private String token;
}