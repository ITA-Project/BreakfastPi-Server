package com.ita.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;


@Slf4j
public class AESUtil {

    private static final String AES_MODE = "AES";

    private AESUtil() {
    }

    public static String encrypt(String content, String secret) {
        try {
            Cipher cipher = Cipher.getInstance(AES_MODE);
            cipher.init(Cipher.ENCRYPT_MODE, getKey(secret));
            byte[] result = cipher.doFinal(content.getBytes());
            return Base64.getEncoder().encodeToString(result);
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static String decrypt(String encryptedContent, String secret) {
        try {
            Cipher cipher = Cipher.getInstance(AES_MODE);
            cipher.init(Cipher.DECRYPT_MODE, getKey(secret));
            byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(encryptedContent));
            return new String(bytes);
        } catch (NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        return null;
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
