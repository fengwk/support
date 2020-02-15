package com.fengwk.support.uc.application.access.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fengwk.support.core.convention.exception.Preconditions;
import com.fengwk.support.core.convention.page.Page;
import com.fengwk.support.core.convention.page.PageQuery;
import com.fengwk.support.core.convention.query.Criteria;
import com.fengwk.support.core.convention.query.Query;
import com.fengwk.support.core.util.ValidationUtils;
import com.fengwk.support.core.util.bean.Property;
import com.fengwk.support.uc.api.access.model.PermissionCreateDTO;
import com.fengwk.support.uc.api.access.model.PermissionDTO;
import com.fengwk.support.uc.api.access.model.PermissionSearchDTO;
import com.fengwk.support.uc.api.access.model.PermissionUpdateDTO;
import com.fengwk.support.uc.api.access.service.PermissionApiService;
import com.fengwk.support.uc.application.access.converter.PermissionConverter;
import com.fengwk.support.uc.domain.access.model.Permission;
import com.fengwk.support.uc.domain.access.repo.CheckedPermissionRepository;
import com.fengwk.support.uc.domain.access.repo.PermissionRepository;
import com.fengwk.support.uc.domain.access.service.RemovePermissionService;

/**
 * 
 * @author fengwk
 */
@Validated
@Transactional
@Service
public class PermissionApiServiceImpl implements PermissionApiService {

    @Autowired
    volatile RemovePermissionService removePermissionService;
    
    @Autowired
    volatile PermissionRepository permissionRepository;
    
    @Override
    public PermissionDTO create(PermissionCreateDTO permissionCreateDTO) {
        Permission permission = Permission.create(permissionCreateDTO.getName(), permissionCreateDTO.getPath());
        new CheckedPermissionRepository(permissionRepository).requiredNonDuplicateName().add(permission);
        return PermissionConverter.convert(permission);
    }

    @Override
    public void remove(long permissionId) {
        removePermissionService.remove(permissionId);
    }

    @Override
    public PermissionDTO updateSelective(PermissionUpdateDTO permissionUpdateDTO) {
        Permission permission = new CheckedPermissionRepository(permissionRepository).requiredNonNull().getById(permissionUpdateDTO.getId());
        permission.update(permissionUpdateDTO.getName(), permissionUpdateDTO.getPath(), true);
        permissionRepository.updateById(permission);
        return PermissionConverter.convert(permission);
    }

    @Override
    public Page<PermissionDTO> search(PermissionSearchDTO searchDTO) {
        Preconditions.isTrue(ValidationUtils.isLegalLike(searchDTO.getName()), "非法的权限名称");
        
        Query<Permission> query = new Query<>();
        Criteria<Permission> criteria = new Criteria<>();
        Optional<PermissionSearchDTO> opt = Optional.of(searchDTO);
        opt.map(PermissionSearchDTO::getName).ifPresent(name -> criteria.andLikePrefix(Property.of(Permission::getName), name));
        query.and(criteria);
        
        return permissionRepository.page(query, new PageQuery(searchDTO)).map(PermissionConverter::convert);
    }

}
