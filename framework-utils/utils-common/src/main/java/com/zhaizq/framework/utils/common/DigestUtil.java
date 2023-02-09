package com.zhaizq.framework.utils.common;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 加密工具类
 *
 * MD5, SHA256     摘要
 * AES             对称加密
 * RSA             非对称加密
 */
public class DigestUtil {
    public static byte[] aesEncrypt(byte[] src, byte[] digest) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(new SecureRandom(digest));

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keyGenerator.generateKey());
        return cipher.doFinal(src);
    }

    public static byte[] aesDecrypt(byte[] src, byte[] digest) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(new SecureRandom(digest));

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keyGenerator.generateKey());
        return cipher.doFinal(src);
    }

    public static String sha256AsHex(String str) {
        return asHex(encrypt(str.getBytes(), "SHA-256"));
    }

    public static String md5AsHex(String str) {
        return asHex(encrypt(str.getBytes(), "MD5"));
    }

    public static String encryptAsHex(String bytes, String enc) {
        return asHex(encrypt(bytes.getBytes(), enc));
    }

    public static String asHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte aByte : bytes)
            result.append((aByte & 0xff) < 16 ? "0" : "").append(Integer.toHexString((aByte & 0xff)));
        return result.toString();
    }

    public static byte[] encrypt(byte[] bytes, String enc) {
        try {
            MessageDigest md = MessageDigest.getInstance(enc);
            md.update(bytes);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}