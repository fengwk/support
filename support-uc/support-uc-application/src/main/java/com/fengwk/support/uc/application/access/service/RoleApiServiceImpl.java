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
import com.fengwk.support.uc.api.access.model.RoleCreateDTO;
import com.fengwk.support.uc.api.access.model.RoleDTO;
import com.fengwk.support.uc.api.access.model.RoleSearchDTO;
import com.fengwk.support.uc.api.access.model.RoleUpdateDTO;
import com.fengwk.support.uc.api.access.service.RoleApiService;
import com.fengwk.support.uc.application.access.converter.RoleConverter;
import com.fengwk.support.uc.domain.access.model.Role;
import com.fengwk.support.uc.domain.access.repo.CheckedRoleRepository;
import com.fengwk.support.uc.domain.access.repo.RoleRepository;
import com.fengwk.support.uc.domain.access.service.RemoveRoleService;

/**
 * 
 * @author fengwk
 */
@Validated
@Transactional
@Service
public class RoleApiServiceImpl implements RoleApiService {
    
    @Autowired
    volatile RemoveRoleService removeRoleService;
    
    @Autowired
    volatile RoleRepository roleRepository;
    
    @Override
    public RoleDTO create(RoleCreateDTO roleCreateDTO) {
        Role role = Role.create(roleCreateDTO.getName());
        new CheckedRoleRepository(roleRepository).requiredNonDuplicateName().add(role);
        return RoleConverter.convert(role);
    }

    @Override
    public void remove(long roleId) {
        removeRoleService.remove(roleId);
    }

    @Override
    public RoleDTO updateSelective(RoleUpdateDTO roleUpdateDTO) {
        Role role = new CheckedRoleRepository(roleRepository).requiredNonNull().getById(roleUpdateDTO.getId());
        role.update(roleUpdateDTO.getName(), true);
        new CheckedRoleRepository(roleRepository).requiredNonDuplicateName().updateById(role);
        return RoleConverter.convert(role);
    }

    @Override
    public Page<RoleDTO> search(RoleSearchDTO searchDTO) {
        Preconditions.isTrue(ValidationUtils.isLegalLike(searchDTO.getName()), "非法的角色名称");
        
        Query<Role> query = new Query<>();
        Criteria<Role> criteria = new Criteria<>();
        Optional<RoleSearchDTO> opt = Optional.of(searchDTO);
        opt.map(RoleSearchDTO::getName).ifPresent(name -> criteria.andLikePrefix(Property.of(Role::getName), name));
        query.and(criteria);
        
        return roleRepository.page(query, new PageQuery(searchDTO)).map(RoleConverter::convert);
    }

}
