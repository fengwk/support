package com.fengwk.support.core.util.security.aes;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * @author fengwk
 */
public class AES {

    private static final String ALGORITHM_AES = "AES";

    private String key;
    
    private Cipher encryptCipher;
    
    private Cipher decryptCipher;
    
    public AES(String key) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
        if (key == null) {
            throw new NullPointerException("Key cannot be null.");
        }
        this.key = key;
        this.encryptCipher = init(Cipher.ENCRYPT_MODE);
        this.decryptCipher = init(Cipher.DECRYPT_MODE);
    }
    
    /**
     * 加密
     * 
     * @param data
     * @return
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public byte[] encrypt(byte[] input) throws IllegalBlockSizeException, BadPaddingException {
        return encryptCipher.doFinal(input);
    }
    
    /**
     * 解密
     * 
     * @param input
     * @return
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public byte[] decrypt(byte[] input) throws IllegalBlockSizeException, BadPaddingException {
        return decryptCipher.doFinal(input);
    }
    
    // 初始化aes编码器
    private Cipher init(int opmode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        KeyGenerator kgen = KeyGenerator.getInstance(ALGORITHM_AES);
        kgen.init(128, new SecureRandom(key.getBytes()));
        SecretKeySpec secretKey = (SecretKeySpec) new SecretKeySpec(kgen.generateKey().getEncoded(), ALGORITHM_AES);
        Cipher cipher = Cipher.getInstance(ALGORITHM_AES);// 创建密码器
        cipher.init(opmode, secretKey);// 初始化
        return cipher;
    }
    
}
