package com.fengwk.support.uc.api.oauth2.model;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 
 * @author fengwk
 */
@Data
public class ClientCreateDTO {

    /**
     * 客户端名称
     */
    @NotBlank
    String name;
    
    /**
     * 重定向规则
     */
    @NotEmpty
    List<RedirectRuleDTO> redirectRules;
    
    /**
     * 访问令牌超时/秒
     */
    @Min(1)
    @NotNull
    Integer accessExpiresIn;
    
    /**
     * 刷新令牌超时/秒
     */
    @Min(1)
    @NotNull
    Integer refreshExpiresIn;
    
    /**
     * 是否为排它模式
     * 共享模式:同一资源持有者在一个客户端下每次授权将产生一枚新令牌,并且不会对该客户端下其他令牌产生影响
     * 排它模式:同一资源持有者在一个客户端下每次授权将产生一枚新令牌,并且会使该客户端下其他令牌失效
     */
    @NotNull
    Boolean isExclusive;
    
    /**
     * 令牌数量限制
     */
    @Min(1)
    @NotNull
    Integer tokenCountLimit;
    
    /**
     * 是否禁用
     */
    @NotNull
    Boolean isDisabled;
    
}
