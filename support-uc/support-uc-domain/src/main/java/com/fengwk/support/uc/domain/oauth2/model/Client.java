package com.fengwk.support.uc.domain.oauth2.model;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;

import com.fengwk.support.core.exception.ExceptionCodes;
import com.fengwk.support.core.util.UuidUtils;
import com.fengwk.support.uc.domain.UcEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户端
 * 
 * @author fengwk
 */
@Slf4j
@EqualsAndHashCode(callSuper = true)
@Data
public class Client extends UcEntity {

    /**
     * 客户端名称
     */
    String name;
    
    /**
     * 客户端密钥
     */
    String secret;
    
    /**
     * 重定向规则
     */
    List<RedirectRule> redirectRules;
    
    /**
     * 访问令牌超时/秒
     */
    int accessExpiresIn;
    
    /**
     * 刷新令牌超时/秒
     */
    int refreshExpiresIn;
    
    /**
     * 是否为排它模式
     * 共享模式:同一资源持有者在一个客户端下每次授权将产生一枚新令牌,并且不会对该客户端下其他令牌产生影响
     * 排它模式:同一资源持有者在一个客户端下每次授权将产生一枚新令牌,并且会使该客户端下其他令牌失效
     */
    boolean isExclusive;
    
    /**
     * 令牌数量限制
     */
    int tokenCountLimit;
    
    /**
     * 是否禁用
     */
    boolean isDisabled;
    
    public static Client of(String name, List<RedirectRule> redirectRules, int accessExpiresIn, int refreshExpiresIn, boolean isExclusive, int tokenCountLimit) {
        Client client = new Client();
        client.name = name;
        client.secret = UuidUtils.genShort();
        client.redirectRules = redirectRules;
        client.accessExpiresIn = accessExpiresIn;
        client.refreshExpiresIn = refreshExpiresIn;
        client.isExclusive = isExclusive;
        client.tokenCountLimit = tokenCountLimit;
        client.isDisabled = false;
        return client;
    }
    
    public void checkSecret(String testSecret) {
        if (!verifySecret(secret)) {
            log.warn("客户端密钥错误,clientId={},testSecret={}.", id, testSecret);
            throw ExceptionCodes.biz().create("客户端密钥错误");
        }
    }
    
    private boolean verifySecret(String secret) {
        if (this.secret == null) {
            return false;
        }
        return Objects.equals(this.secret, secret);
    }
    
    public void checkRedirectUri(URI redirectUri) {
        if (!verifyRedirectUri(redirectUri)) {
            log.warn("重定向地址不符合客户端要求,clientId={},redirectUri={}.", id, redirectUri);
            throw ExceptionCodes.biz().create("重定向地址不符合客户端要求");
        }
    }
    
    private boolean verifyRedirectUri(URI redirectUri) {
        if (CollectionUtils.isEmpty(redirectRules)) {
            return false;
        }
        for (RedirectRule redirectRule : redirectRules) {
            if (redirectRule.verify(redirectUri)) {
                return true;
            }
        }
        return false;
    }

}
