package com.fengwk.support.uc.infrastructure.mysql.oauth2.converter;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.fengwk.support.core.gson.GsonUtils;
import com.fengwk.support.core.util.ConvertUtils;
import com.fengwk.support.uc.domain.oauth2.model.Client;
import com.fengwk.support.uc.domain.oauth2.model.RedirectRule;
import com.fengwk.support.uc.infrastructure.mysql.UcConverter;
import com.fengwk.support.uc.infrastructure.mysql.oauth2.model.ClientPO;
import com.google.gson.reflect.TypeToken;

import lombok.Data;

/**
 * 
 * @author fengwk
 */
@Component
public class ClientConverter implements UcConverter<Client, ClientPO> {
    
    @Override
    public ClientPO convert(Client client) {
        if (client == null) {
            return null;
        }
        ClientPO clientPO = new ClientPO();
        clientPO.setId(client.getId());
        clientPO.setCreatedTime(client.getCreatedTime());
        clientPO.setModifiedTime(client.getModifiedTime());
        
        clientPO.setName(client.getName());
        clientPO.setSecret(client.getSecret());
        clientPO.setRedirectRules(convert(client.getRedirectRules()));
        clientPO.setAccessExpiresIn(client.getAccessExpiresIn());
        clientPO.setRefreshExpiresIn(client.getRefreshExpiresIn());
        clientPO.setIsExclusive(ConvertUtils.boolToInt(client.isExclusive()));  
        clientPO.setTokenCountLimit(client.getTokenCountLimit());
        clientPO.setIsDisabled(ConvertUtils.boolToInt(client.isDisabled()));
        
        return clientPO;
    }
    
    @Override
    public Client convert(ClientPO clientPO) {
        if (clientPO == null) {
            return null;
        }
        Client client = new Client();
        client.setId(clientPO.getId());
        client.setCreatedTime(clientPO.getCreatedTime());
        client.setModifiedTime(clientPO.getModifiedTime());
        
        client.setName(clientPO.getName());
        client.setSecret(clientPO.getSecret());
        client.setRedirectRules(convert(clientPO.getRedirectRules()));
        client.setAccessExpiresIn(clientPO.getAccessExpiresIn());
        client.setRefreshExpiresIn(clientPO.getRefreshExpiresIn());
        client.setExclusive(ConvertUtils.intToBool(clientPO.getIsExclusive()));
        client.setTokenCountLimit(clientPO.getTokenCountLimit());
        client.setDisabled(ConvertUtils.intToBool(clientPO.getIsDisabled()));

        return client;
    }
    
    private static String convert(List<RedirectRule> redirectRules) {
        if (CollectionUtils.isEmpty(redirectRules)) {
            return null;
        }
        return GsonUtils.toJson(redirectRules.stream().map(ClientConverter::convert).collect(Collectors.toList()));
    }
    
    private static List<RedirectRule> convert(String redirectRules) {
        if (StringUtils.isBlank(redirectRules)) {
            return null;
        }
        List<RedirectRulePO> redirectRulePOs = GsonUtils.fromJson(redirectRules, new TypeToken<List<RedirectRulePO>>() {}.getType());
        return redirectRulePOs.stream().map(ClientConverter::convert).collect(Collectors.toList());
    }
    
    private static RedirectRulePO convert(RedirectRule redirectRule) {
        if (redirectRule == null) {
            return null;
        }
        RedirectRulePO redirectRulePO = new RedirectRulePO();
        redirectRulePO.setMode(redirectRule.getMode().getCode());
        if (redirectRule.getValue() != null) {
            redirectRulePO.setValue(redirectRule.getValue().toString());
        }
        return redirectRulePO;
    }
    
    private static RedirectRule convert(RedirectRulePO redirectRulePO) {
        if (redirectRulePO == null) {
            return null;
        }
        return new RedirectRule(RedirectRule.Mode.getByCode(redirectRulePO.getMode()), StringUtils.isBlank(redirectRulePO.getValue()) ? null : URI.create(redirectRulePO.getValue()));
    }
    
    @Data
    static class RedirectRulePO {
        
        Integer mode;
        String value;
        
    }

}
