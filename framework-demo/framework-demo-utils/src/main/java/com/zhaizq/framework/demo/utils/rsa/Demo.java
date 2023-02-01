package com.zhaizq.framework.demo.utils.rsa;

import com.zhaizq.framework.utils.common.StringRsaUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Demo {
    public static void main(String[] args) throws NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, IOException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        StringRsaUtil.Keys keys = StringRsaUtil.genKeyPair();
        System.out.println(keys);

        String s = StringRsaUtil.encryptByPublicKey("zhaizq 中文!", keys.getPublicKey());
        String s1 = StringRsaUtil.decryptByPrivateKey(s, keys.getPrivateKey());
        System.out.println(s1);
    }
}