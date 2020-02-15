package com.fengwk.support.core.util.security.rsa;

import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/**
 * 
 * @author fengwk
 */
public class RSAPublicKey extends AbstractRSAKey {

    protected RSAPublicKey(Key key) {
        super(key);
    }

    public RSAPublicKey(byte[] encoded) throws NoSuchAlgorithmException, InvalidKeySpecException {
        super(encoded);
    }

    public RSAPublicKey(String base64Encoded) throws NoSuchAlgorithmException, InvalidKeySpecException {
        super(base64Encoded);
    }

    @Override
    protected Key generateKey(byte[] encoded) throws NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(encoded);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA.ALGORITHM_RSA);
        return keyFactory.generatePublic(spec);
    }

}
