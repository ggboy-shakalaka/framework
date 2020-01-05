package cn.zhaizq.sso.sdk.domain.response;

import cn.zhaizq.sso.sdk.domain.SsoUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SsoCheckResult {
    private Status status;
    private SsoUser ssoUser;

    public enum Status {
        _1("通过"),
        _2("权限不足"),
        _3("登录状态过期"),
        ;

        Status(String remark){
        }
    }
}