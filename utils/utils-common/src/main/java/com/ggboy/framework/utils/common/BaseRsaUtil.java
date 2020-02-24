package com.ggboy.framework.utils.common;

import com.ggboy.framework.utils.exception.RSAException;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author RSA签名,加解密处理核心文件,注意:密钥长度1024
 */
public class BaseRsaUtil {
    // 签名算法
    public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    // 加密算法RSA
    public static final String KEY_ALGORITHM = "RSA";
    // RSA最大加密明文大小
    private static final int MAX_ENCRYPT_BLOCK = 117;
    // RSA最大解密密文大小
    private static final int MAX_DECRYPT_BLOCK = 128;

    private static KeyFactory keyFactory;

    private static KeyFactory getKeyFactory() throws NoSuchAlgorithmException {
        return keyFactory == null ? keyFactory = KeyFactory.getInstance(KEY_ALGORITHM) : keyFactory;
    }

    private static PublicKey generatePublic(byte[] key) throws InvalidKeySpecException, NoSuchAlgorithmException {
        return getKeyFactory().generatePublic(new X509EncodedKeySpec(key));
    }

    private static PrivateKey generatePrivate(byte[] key) throws InvalidKeySpecException, NoSuchAlgorithmException {
        return getKeyFactory().generatePrivate(new PKCS8EncodedKeySpec(key));
    }

    public static byte[] sign(byte[] data, byte[] privateKey) throws SignatureException, InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(generatePrivate(privateKey));
        signature.update(data);
        return signature.sign();
    }

    public static boolean verify(byte[] data, byte[] sign, byte[] publicKey) throws SignatureException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(generatePublic(publicKey));
        signature.update(data);
        return signature.verify(sign);
    }

    public static byte[] encryptByPublicKey(byte[] data, byte[] key) throws InvalidKeySpecException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IOException {
        return rsaAlgorithm(data, generatePublic(key), Cipher.ENCRYPT_MODE, MAX_ENCRYPT_BLOCK);
    }

    public static byte[] encryptByPrivateKey(byte[] data, byte[] key) throws InvalidKeySpecException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IOException {
        return rsaAlgorithm(data, generatePrivate(key), Cipher.ENCRYPT_MODE, MAX_ENCRYPT_BLOCK);
    }

    public static byte[] decryptByPublicKey(byte[] data, byte[] key) throws InvalidKeySpecException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IOException {
        return rsaAlgorithm(data, generatePublic(key), Cipher.DECRYPT_MODE, MAX_DECRYPT_BLOCK);
    }

    public static byte[] decryptByPrivateKey(byte[] data, byte[] key) throws InvalidKeySpecException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IOException {
        return rsaAlgorithm(data, generatePrivate(key), Cipher.DECRYPT_MODE, MAX_DECRYPT_BLOCK);
    }

    /**
     * 密文算法
     *
     * @param data
     * @param key
     * @param mode
     * @return byte[]
     * @throws RSAException
     */
    private static byte[] rsaAlgorithm(byte[] data, Key key, int mode, int block) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException {
        try(ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Cipher cipher = Cipher.getInstance(getKeyFactory().getAlgorithm());
            cipher.init(mode, key);
            int inputLen = data.length;
            int offSet = 0;
            byte[] cache = null;
            int i = 0;
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > block) {
                    cache = cipher.doFinal(data, offSet, block);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * block;
            }
            return out.toByteArray();
        }
    }

    /**
     * 生成秘钥对
     *
     * @return
     * @throws RSAException
     * @throws Exception
     */
    public static Keys genKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        return new Keys(keyPair.getPublic().getEncoded(), keyPair.getPrivate().getEncoded());
    }

    @Data
    @AllArgsConstructor
    public static class Keys {
        private byte[] publicKey;
        private byte[] privateKey;
    }
}