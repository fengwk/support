package com.fengwk.support.uc.infrastructure.mysql.user.model;

import javax.persistence.Table;

import com.fengwk.support.uc.infrastructure.mysql.UcPO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author fengwk
 */
@Table(name = "User")
@EqualsAndHashCode(callSuper = true)
@Data
public class UserPO extends UcPO {
    
    String email;
    String nickname;
    String password;
    
}
