package com.icbc.match.utils;

import com.icbc.api.commons.codec.Base64;
import com.icbc.match.exception.TransactionException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {
    private static final String AES_ALG = "AES";
    private static final String AES_CBC_PCK_ALG = "AES/CBC/PKCS5Padding";
    private static final byte[] AES_IV = initIv("AES/CBC/PKCS5Padding");
    private static final String CHARSET_UTF8 = "UTF-8";

    public AESUtil() {
    }

    public static String aesEncrypt(String content, String aesKey) {
        return aesEncrypt(content, aesKey, "UTF-8");
    }

    public static String aesEncrypt(String content, String aesKey, String charset) {
        try {
            return new String(aesEncrypt(content.getBytes(charset), aesKey));
        } catch (Exception var4) {
            throw new TransactionException("-1", "AES加密失败");
        }
    }

    public static byte[] aesEncrypt(byte[] content, String aesKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(AES_IV);
            cipher.init(1, new SecretKeySpec(Base64.decodeBase64(aesKey), "AES"), iv);
            byte[] encryptBytes = cipher.doFinal(content);
            return Base64.encodeBase64(encryptBytes);
        } catch (Exception var5) {
            throw new TransactionException("-1", "AES加密失败");
        }
    }

    public static String aesDecrypt(String content, String aesKey) {
        return aesDecrypt(content, aesKey, "UTF-8");
    }

    public static byte[] aesDecrypt(byte[] content, String aesKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(AES_IV);
            cipher.init(2, new SecretKeySpec(Base64.decodeBase64(aesKey), "AES"), iv);
            return cipher.doFinal(Base64.decodeBase64(content));
        } catch (Exception var4) {
            throw new TransactionException("-1", "AES解密失败");
        }
    }

    public static String aesDecrypt(String content, String aesKey, String charset) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(AES_IV);
            cipher.init(2, new SecretKeySpec(Base64.decodeBase64(aesKey), "AES"), iv);
            byte[] cleanBytes = cipher.doFinal(Base64.decodeBase64(content));
            return new String(cleanBytes, charset);
        } catch (Exception var6) {
            throw new TransactionException("-1", "AES解密失败");
        }
    }

    private static byte[] initIv(String fullAlg) {
        byte[] iv;
        int i;
        try {
            Cipher cipher = Cipher.getInstance(fullAlg);
            int blockSize = cipher.getBlockSize();
            iv = new byte[blockSize];

            for (i = 0; i < blockSize; ++i) {
                iv[i] = 0;
            }

            return iv;
        } catch (Exception var5) {
            int blockSize = 16;
            iv = new byte[blockSize];

            for (i = 0; i < blockSize; ++i) {
                iv[i] = 0;
            }

            return iv;
        }
    }
}