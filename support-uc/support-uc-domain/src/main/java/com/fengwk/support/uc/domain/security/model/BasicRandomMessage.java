package com.fengwk.support.uc.domain.security.model;

import org.apache.commons.lang3.StringUtils;

import com.fengwk.support.core.domain.model.ValueObject;

import lombok.Data;

/**
 * 
 * @author fengwk
 */
@Data
abstract class BasicRandomMessage implements ValueObject {

    protected static final String BANNER = "【fengwk】";
    
    final Random.Type type;
    final int expiresIn;
    final String target;
    final String value;
    
    public String getText() {
        return BANNER + "您的" + type.getValue() + "验证码是" + value + formatExpiresIn() + "，千万不要向陌生人透露哦。";
    }
    
    protected String formatExpiresIn() {
        if (expiresIn <= 0) {
            return StringUtils.EMPTY;
        } else if (expiresIn > 60 && expiresIn % 60 == 0) {
            return "，请在" + (expiresIn / 60) + "分钟内完成验证";
        } else {
            return "，请在" + expiresIn + "秒内完成验证";
        }
    }
    
}
