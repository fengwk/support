package com.fengwk.support.uc.domain.security.facade;

import com.fengwk.support.uc.domain.security.model.EmailRandomMessage;
import com.fengwk.support.uc.domain.security.model.SMSRandomMessage;

/**
 * 
 * @author fengwk
 */
public interface RandomMessageFacade {

    void sendToEmail(EmailRandomMessage emailMessage);
    
    void sendToSMS(SMSRandomMessage smsMessage);
    
}
