package com.fengwk.support.uc.infrastructure.mysql.access.converter;

import org.springframework.stereotype.Component;

import com.fengwk.support.uc.domain.access.model.UserRoleLink;
import com.fengwk.support.uc.infrastructure.mysql.UcConverter;
import com.fengwk.support.uc.infrastructure.mysql.access.model.UserRoleLinkPO;

/**
 * 
 * @author fengwk
 */
@Component
public class UserRoleLinkConverter implements UcConverter<UserRoleLink, UserRoleLinkPO> {

    @Override
    public UserRoleLinkPO convert(UserRoleLink entity) {
        if (entity == null) {
            return null;
        }
        UserRoleLinkPO userRoleLinkPO = new UserRoleLinkPO();
        userRoleLinkPO.setCreatedTime(entity.getCreatedTime());
        userRoleLinkPO.setId(entity.getId());
        userRoleLinkPO.setModifiedTime(entity.getModifiedTime());
        userRoleLinkPO.setRoleId(entity.getRoleId());
        userRoleLinkPO.setUserId(entity.getUserId());
        return userRoleLinkPO;
    }

    @Override
    public UserRoleLink convert(UserRoleLinkPO po) {
        if (po == null) {
            return null;
        }
        UserRoleLink userRoleLink = new UserRoleLink();
        userRoleLink.setCreatedTime(po.getCreatedTime());
        userRoleLink.setId(po.getId());
        userRoleLink.setModifiedTime(po.getModifiedTime());
        userRoleLink.setRoleId(po.getRoleId());
        userRoleLink.setUserId(po.getUserId());
        return userRoleLink;
    }

}
