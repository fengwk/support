package com.fengwk.support.uc.domain.oauth2.repo;

import com.fengwk.support.core.domain.repo.CheckedRepository;
import com.fengwk.support.uc.domain.oauth2.model.Client;

/**
 * 
 * @author fengwk
 */
public class CheckedClientRepository extends CheckedRepository<Client, ClientRepository> {

    public CheckedClientRepository(ClientRepository repository) {
        super(repository);
    }

    public Client getById(long id) {
        Client client = repository().getById(id);
        checkAndThrowIfNecessary(client, () -> args(arg("clientId", id)));
        return client;
    }
    
    public CheckedClientRepository requiredNonNull() {
        appendChecker(client -> client == null ? failure("客户端不存在") : success());
        return this;
    }
    
}
