package com.fengwk.support.uc.domain.user.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

/**
 * md5加密实现
 * 
 * @author fengwk
 */
@Service
public class Md5EncryptionService implements EncryptionService {

    @Override
    public String encryptPassword(String cleartextPassword) {
        return DigestUtils.md5Hex(cleartextPassword);
    }

}
