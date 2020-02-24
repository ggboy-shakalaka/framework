package demo.rsa;

import com.ggboy.framework.utils.common.BaseRsaUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Demo {
    public static void main(String[] args) throws NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, IOException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        String str = "zhaizq";
        BaseRsaUtil.Keys keys = BaseRsaUtil.genKeyPair();
        byte[] bytes = BaseRsaUtil.encryptByPublicKey(str.getBytes(), keys.getPublicKey());

        byte[] bytes1 = BaseRsaUtil.decryptByPrivateKey(bytes, keys.getPrivateKey());

        System.out.println(new String(bytes1));
    }
}