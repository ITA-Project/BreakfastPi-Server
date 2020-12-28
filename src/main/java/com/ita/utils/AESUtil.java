package com.ita.utils;

import lombok.extern.log4j.Log4j2;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;


@Log4j2
public class AESUtil {

    private static final String AES_MODE = "AES";

    private AESUtil() {
    }

    public static String encrypt(String content, String secret) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(AES_MODE);
        cipher.init(Cipher.ENCRYPT_MODE, getKey(secret));
        byte[] result = cipher.doFinal(content.getBytes());
        return Base64.getEncoder().encodeToString(result);
    }

    public static String decrypt(String encryptedContent, String secret) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(AES_MODE);
        cipher.init(Cipher.DECRYPT_MODE, getKey(secret));
        byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(encryptedContent));
        return new String(bytes);
    }

    private static Key getKey(String secret) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_MODE);
        SecureRandom sha1PRNG = SecureRandom.getInstance("SHA1PRNG");
        sha1PRNG.setSeed(secret.getBytes());
        keyGenerator.init(128, sha1PRNG);
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] key = secretKey.getEncoded();
        return new SecretKeySpec(key, "AES");
    }
}
