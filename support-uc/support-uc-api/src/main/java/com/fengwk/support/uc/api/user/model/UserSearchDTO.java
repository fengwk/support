package com.fengwk.support.uc.api.user.model;

import com.fengwk.support.core.convention.page.PageQueryDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author fengwk
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserSearchDTO extends PageQueryDTO {

    /**
     * 用户邮箱,非空情况下将作为查询条件
     */
    String email;
    
    /**
     * 用户昵称,非空情况下将作为查询条件
     */
    String nickname;
    
}
