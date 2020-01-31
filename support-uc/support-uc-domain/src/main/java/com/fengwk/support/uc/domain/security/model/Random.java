package com.fengwk.support.uc.domain.security.model;

import java.util.Objects;

import com.fengwk.support.core.util.DateUtils;
import com.fengwk.support.core.util.RandomUtils;
import com.fengwk.support.uc.domain.UcEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 
 * @author fengwk
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Random extends UcEntity {

    static final String[] DIC = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9" };
    
    Way way;
    Type type;
    String target;
    String value;
    int expiresIn;// 超时时间/秒
    Status status;
    
    public static Random of(Way way, Type type, String target, int expiresIn) {
        Random random = new Random();
        random.way = way;
        random.type = type;
        random.target = target;
        random.expiresIn = expiresIn;
        random.value = genRandomValue();
        random.status = Status.UNUSED;
        return random;
    }
    
    static String genRandomValue() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            builder.append(DIC[RandomUtils.nextInt(0, DIC.length)]);
        }
        return builder.toString();
    }
    
    public void refresh(int expiresIn) {
        this.expiresIn = expiresIn;
        this.value = genRandomValue();
        this.status = Status.UNUSED;
    }
    
    public long expiresTime() {
        return DateUtils.toUnixMs(modifiedTime) + expiresIn * 1000;
    }
    
    public boolean isExpired() {
        return System.currentTimeMillis() > expiresTime();
    }
    
    public boolean verify(String testValue) {
        return Objects.equals(this.value, testValue);
    }
    
    public boolean isUnused() {
        return status == Status.UNUSED;
    }
    
    public boolean isSilenced() {
        return status == Status.SILENCED;
    }
    
    public boolean isUsed() {
        return status == Status.USED;
    }

    public void silence() {
        this.status = Status.SILENCED;
    }
    
    public void use() {
        status = Status.USED;
    }
    
    @Getter
    public static enum Way {
        
        EMAIL(1), SMS(2);
        
        final int code;

        private Way(int code) {
            this.code = code;
        }
        
        public static Way getByCode(Integer code) {
            if (code == null) {
                return null;
            }
            for (Way e : Way.values()) {
                if (Objects.equals(e.getCode(), code)) {
                    return e;
                }
            }
            return null;
        }
        
    }
    
    @AllArgsConstructor
    @Getter
    public static enum Type {
        
        REGISTER(1, "注册"), AUTH(2, "登录"), RESET(3, "重置");
        
        final int code;
        final String value;

        public static Type getByCode(Integer code) {
            if (code == null) {
                return null;
            }
            for (Type e : Type.values()) {
                if (Objects.equals(e.getCode(), code)) {
                    return e;
                }
            }
            return null;
        }
        
    }
    
    @AllArgsConstructor
    @Getter
    public static enum Status {
        
        UNUSED(1), SILENCED(2), USED(3);
        
        final int code;

        public static Status getByCode(Integer code) {
            if (code == null) {
                return null;
            }
            for (Status e : Status.values()) {
                if (Objects.equals(e.getCode(), code)) {
                    return e;
                }
            }
            return null;
        }
        
    }
    
}
