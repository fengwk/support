package com.fengwk.support.core.domain.repo;

import org.apache.commons.lang3.ArrayUtils;

import com.fengwk.support.core.domain.exception.DomainException;
import com.fengwk.support.core.util.Checked;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author fengwk
 */
@Slf4j
public class CheckedRepository<T, R> extends Checked<T> {

    protected final R repository;
    
    public CheckedRepository(R repository) {
        this.repository = repository;
    }
    
    protected R repository() {
        return repository;
    }

    @Override
    protected void throwOnFailure(Result result, PrintableArg... args) {
        int argsLen = ArrayUtils.isEmpty(args) ? 0 : args.length;
        Object[] logArgs = new Object[argsLen + 1];
        logArgs[0] = result.getMessage();
        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("{}");
        if (argsLen > 0) {
            for (int i = 0; i < args.length; i++) {
                logBuilder.append(", ").append(args[i].getName()).append("={}");
                logArgs[i + 1] = args[i].getValue();
            }
        }
        logBuilder.append('.');
        log.error(logBuilder.toString(), logArgs);
        throw new DomainException(result.getMessage());
    }
    
}
