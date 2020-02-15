package com.fengwk.support.core.util.security.rsa;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @author fengwk
 */
public class RSA {

    protected static final String ALGORITHM_RSA = "RSA";

    private RSAPublicKey publicKey;

    private RSAPrivateKey privateKey;

    /**
     * 
     * @param keySize >=512,推荐使用2048
     * @throws NoSuchAlgorithmException
     */
    public RSA(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM_RSA);
        keyPairGenerator.initialize(keySize);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        this.publicKey = new RSAPublicKey(keyPair.getPublic());
        this.privateKey = new RSAPrivateKey(keyPair.getPrivate());
    }

    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    public RSAPrivateKey getPrivateKey() {
        return privateKey;
    }
    
}
