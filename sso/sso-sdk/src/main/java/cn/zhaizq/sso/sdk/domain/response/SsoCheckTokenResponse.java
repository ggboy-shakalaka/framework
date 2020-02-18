package cn.zhaizq.sso.sdk.domain.response;

import cn.zhaizq.sso.sdk.domain.SsoUser;
import com.ggboy.framework.common.domain.DataResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SsoCheckTokenResponse extends DataResponse<SsoUser> {
}