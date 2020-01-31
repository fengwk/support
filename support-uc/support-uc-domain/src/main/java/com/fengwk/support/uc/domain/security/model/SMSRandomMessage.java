package com.fengwk.support.uc.domain.security.model;

import com.fengwk.support.uc.domain.security.model.Random.Type;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SMSRandomMessage extends BasicRandomMessage {
    
    SMSRandomMessage(Type type, int expiresIn, String target, String value) {
        super(type, expiresIn, target, value);
    }
    
    public static SMSRandomMessage from(Random random) {
        return new SMSRandomMessage(
                random.getType(), 
                random.getExpiresIn(), 
                random.getTarget(), 
                random.getValue());
    }

}
