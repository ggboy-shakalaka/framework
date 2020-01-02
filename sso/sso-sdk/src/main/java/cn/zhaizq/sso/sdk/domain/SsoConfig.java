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
    private static final long serialVersionUID = 1L;

    public String serverPath;
    public String loginPath;
    public String logoutPath;

    public byte[] toByte() throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new ByteArrayOutputStream());
        objectOutputStream.writeObject(this);
        return null;
    }
}