package com.fengwk.support.core.security.rsa;

import java.io.ByteArrayOutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.fengwk.support.core.exception.Preconditions;

/**
 * 
 * @author fengwk
 */
public abstract class AbstractRSAKey {

    private Key key;

    protected AbstractRSAKey(Key key) {
        this.key = key;
    }

    public AbstractRSAKey(byte[] encoded) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Preconditions.notNull(encoded, "Encoded cannot be null.");
        this.key = generateKey(encoded);
    }

    public AbstractRSAKey(String base64Encoded) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Preconditions.notNull(base64Encoded, "秘钥内容不能为空");
        this.key = generateKey(Base64.getDecoder().decode(base64Encoded));
    }

    public byte[] getEncoded() {
        return key.getEncoded();
    }

    public String getBase64Encoded() {
        return Base64.getEncoder().encodeToString(getEncoded());
    }

    public byte[] encrypt(byte[] data) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(RSA.ALGORITHM_RSA);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data,
                ((java.security.interfaces.RSAKey) key).getModulus().bitLength());
    }

    public byte[] decrypt(byte[] data) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(RSA.ALGORITHM_RSA);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, data,
                ((java.security.interfaces.RSAKey) key).getModulus().bitLength());
    }

    protected abstract Key generateKey(byte[] encoded) throws NoSuchAlgorithmException, InvalidKeySpecException;

    /**
     * 分割运算RSA加密算法对于加密数据的长度是有要求的,一般来说,明文长度小于等于密钥长度(byte)-11 将数据分割为合适长度再进行加密
     * 
     * @param cipher
     * @param opmode
     * @param datas
     * @param keySize
     * @return
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    private byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] data, int keySize)
            throws IllegalBlockSizeException, BadPaddingException {
        // 计算单片长度
        int maxBlock;
        if (opmode == Cipher.DECRYPT_MODE) {
            maxBlock = keySize / 8;
        } else {
            maxBlock = keySize / 8 - 11;
        }
        // 构建输出字节流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        int i = 0;
        byte[] buff;
        while (data.length > offSet) {
            if (data.length - offSet > maxBlock) {
                buff = cipher.doFinal(data, offSet, maxBlock);
            } else {
                buff = cipher.doFinal(data, offSet, data.length - offSet);
            }
            out.write(buff, 0, buff.length);
            i++;
            offSet = i * maxBlock;
        }
        return out.toByteArray();
    }

}
