package com.fengwk.support.core.util.security.rsa;

import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * 
 * @author fengwk
 */
public class RSAPrivateKey extends AbstractRSAKey {

    protected RSAPrivateKey(Key key) {
        super(key);
    }

    public RSAPrivateKey(byte[] encoded) throws NoSuchAlgorithmException, InvalidKeySpecException {
        super(encoded);
    }

    public RSAPrivateKey(String base64Encoded) throws NoSuchAlgorithmException, InvalidKeySpecException {
        super(base64Encoded);
    }

    @Override
    protected Key generateKey(byte[] encoded) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(encoded);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA.ALGORITHM_RSA);
        return keyFactory.generatePrivate(spec);
    }

}
