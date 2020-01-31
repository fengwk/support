package com.fengwk.support.uc.domain.security.model;

import com.fengwk.support.uc.domain.security.model.Random.Type;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author fengwk
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EmailRandomMessage extends BasicRandomMessage {

    EmailRandomMessage(Type type, int expiresIn, String target, String value) {
        super(type, expiresIn, target, value);
    }

    public static EmailRandomMessage from(Random random) {
        return new EmailRandomMessage(
                random.getType(), 
                random.getExpiresIn(), 
                random.getTarget(), 
                random.getValue());
    }
    
    public String getSubject() {
        return BANNER + type.getValue() + "验证码";
    }
    
}
