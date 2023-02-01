package com.zhaizq.framework.utils.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class StringRsaUtil {
    public static String sign(String data, String privateKey) throws SignatureException, InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException {
        byte[] sign = BaseRsaUtil.sign(data.getBytes(), Base64.getDecoder().decode(privateKey));
        return Base64.getEncoder().encodeToString(sign);
    }

    public static boolean verify(String data, String sign, String publicKey) throws SignatureException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        return BaseRsaUtil.verify(data.getBytes(), Base64.getDecoder().decode(sign), Base64.getDecoder().decode(publicKey));
    }

    public static String encryptByPublicKey(String data, String key) throws InvalidKeySpecException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IOException {
        byte[] bytes = BaseRsaUtil.encryptByPublicKey(data.getBytes(), Base64.getDecoder().decode(key));
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static String encryptByPrivateKey(String data, String key) throws InvalidKeySpecException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IOException {
        byte[] bytes = BaseRsaUtil.encryptByPrivateKey(data.getBytes(), Base64.getDecoder().decode(key));
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static String decryptByPublicKey(String data, String key) throws InvalidKeySpecException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IOException {
        byte[] bytes = BaseRsaUtil.decryptByPublicKey(Base64.getDecoder().decode(data), Base64.getDecoder().decode(key));
        return new String(bytes);
    }

    public static String decryptByPrivateKey(String data, String key) throws InvalidKeySpecException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IOException {
        byte[] bytes = BaseRsaUtil.decryptByPrivateKey(Base64.getDecoder().decode(data), Base64.getDecoder().decode(key));
        return new String(bytes);
    }

    public static StringRsaUtil.Keys genKeyPair() throws NoSuchAlgorithmException {
        BaseRsaUtil.Keys keys = BaseRsaUtil.genKeyPair();
        return new Keys(Base64.getEncoder().encodeToString(keys.getPublicKey()), Base64.getEncoder().encodeToString(keys.getPrivateKey()));
    }

    @Data
    @AllArgsConstructor
    public static class Keys {
        private String publicKey;
        private String privateKey;
    }
}