package com.fengwk.support.uc.application.oauth2.service;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
import com.fengwk.support.uc.api.oauth2.model.ClientCreateDTO;
import com.fengwk.support.uc.api.oauth2.model.ClientDTO;
import com.fengwk.support.uc.api.oauth2.model.ClientSearchDTO;
import com.fengwk.support.uc.api.oauth2.model.ClientUpdateDTO;
import com.fengwk.support.uc.api.oauth2.model.RedirectRuleDTO;
import com.fengwk.support.uc.api.oauth2.service.ClientApiService;
import com.fengwk.support.uc.domain.oauth2.model.Client;
import com.fengwk.support.uc.domain.oauth2.model.RedirectRule;
import com.fengwk.support.uc.domain.oauth2.repo.CheckedClientRepository;
import com.fengwk.support.uc.domain.oauth2.repo.ClientRepository;

/**
 * 
 * @author fengwk
 */
@Validated
@Transactional
@Service
public class ClientApiServiceImpl implements ClientApiService {

    @Autowired
    volatile ClientRepository clientRepository;
    
    @Override
    public ClientDTO create(ClientCreateDTO createDTO) {
        Client client = Client.create(
                createDTO.getName(), 
                convertToRules(createDTO.getRedirectRules()), 
                createDTO.getAccessExpiresIn(), 
                createDTO.getRefreshExpiresIn(), 
                createDTO.getIsExclusive(), 
                createDTO.getTokenCountLimit());
        clientRepository.add(client);
        return convert(client);
    }

    @Override
    public void remove(long clientId) {
        new CheckedClientRepository(clientRepository).requiredNonNull().getById(clientId);
        clientRepository.removeById(clientId);
    }
    
    @Override
    public String refreshSecret(long clientId) {
        Client client = new CheckedClientRepository(clientRepository).requiredNonNull().getById(clientId);
        client.refreshSecret();
        clientRepository.updateById(client);
        return client.getSecret();
    }

    @Override
    public ClientDTO updateSelective(ClientUpdateDTO updateDTO) {
        Client client = new CheckedClientRepository(clientRepository).requiredNonNull().getById(updateDTO.getId());
        client.update(
                updateDTO.getName(), 
                convertToRules(updateDTO.getRedirectRules()), 
                updateDTO.getAccessExpiresIn(), 
                updateDTO.getRefreshExpiresIn(), 
                updateDTO.getIsExclusive(), 
                updateDTO.getTokenCountLimit(), 
                updateDTO.getIsDisabled(),
                true);
        clientRepository.updateById(client);
        return convert(client);
    }

    @Override
    public Page<ClientDTO> search(ClientSearchDTO searchDTO) {
        Preconditions.isTrue(ValidationUtils.isLegalLike(searchDTO.getName()), "非法的客户端名称");
        
        Query<Client> query = new Query<>();
        Criteria<Client> criteria = new Criteria<>();
        Optional<ClientSearchDTO> opt = Optional.of(searchDTO);
        opt.map(ClientSearchDTO::getName).ifPresent(name -> criteria.andLikePrefix(Property.of(Client::getName), name));
        query.and(criteria);
        
        return clientRepository.page(query, new PageQuery(searchDTO)).map(this::convert);
    }
    
    private List<RedirectRule> convertToRules(Collection<RedirectRuleDTO> ruleDTOs) {
        if (CollectionUtils.isEmpty(ruleDTOs)) {
            return Collections.emptyList();
        }
        return ruleDTOs.stream().map(this::convert).collect(Collectors.toList());
    }
    
    private List<RedirectRuleDTO> convertToRuleDTOs(Collection<RedirectRule> rules) {
        if (CollectionUtils.isEmpty(rules)) {
            return Collections.emptyList();
        }
        return rules.stream().map(this::convert).collect(Collectors.toList());
    }
    
    private RedirectRule convert(RedirectRuleDTO ruleDTO) {
        return new RedirectRule(
                RedirectRule.Mode.getByCode(ruleDTO.getMode()),
                StringUtils.isBlank(ruleDTO.getValue()) ? null : URI.create(ruleDTO.getValue()));
    }
    
    private RedirectRuleDTO convert(RedirectRule rule) {
        RedirectRuleDTO ruleDTO = new RedirectRuleDTO();
        ruleDTO.setMode(rule.getMode().getCode());
        ruleDTO.setValue(rule.getValue() == null ? null : rule.getValue().toString());
        return ruleDTO;
    }
    
    private ClientDTO convert(Client client) {
        if (client == null) {
            return null;
        }
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(client.getId());
        clientDTO.setCreatedTime(client.getCreatedTime());
        clientDTO.setModifiedTime(client.getModifiedTime());
        clientDTO.setAccessExpiresIn(client.getAccessExpiresIn());
        clientDTO.setIsDisabled(client.isDisabled());
        clientDTO.setIsExclusive(client.isExclusive());
        clientDTO.setName(client.getName());
        clientDTO.setRedirectRules(convertToRuleDTOs(client.getRedirectRules()));
        clientDTO.setRefreshExpiresIn(client.getRefreshExpiresIn());
        clientDTO.setSecret(client.getSecret());
        clientDTO.setTokenCountLimit(client.getTokenCountLimit());
        return clientDTO;
    }

}
