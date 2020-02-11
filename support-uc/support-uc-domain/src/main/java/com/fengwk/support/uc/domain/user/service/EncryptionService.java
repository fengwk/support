package com.fengwk.support.uc.domain.user.service;

/**
 * 加密服务
 * 
 * @author fengwk
 */
public interface EncryptionService {
    
    String encryptPassword(String cleartextPassword);
    
}
