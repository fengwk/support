package com.fengwk.support.uc.infrastructure.mysql.security.converter;

import org.springframework.stereotype.Component;

import com.fengwk.support.core.util.ConvertUtils;
import com.fengwk.support.uc.domain.security.model.Random;
import com.fengwk.support.uc.infrastructure.mysql.UcConverter;
import com.fengwk.support.uc.infrastructure.mysql.security.model.RandomPO;

/**
 * 
 * @author fengwk
 */
@Component
public class RandomConverter implements UcConverter<Random, RandomPO> {

    @Override
    public RandomPO convert(Random entity) {
        if (entity == null) {
            return null;
        }
        RandomPO randomPO = new RandomPO();
        randomPO.setCreatedTime(entity.getCreatedTime());
        randomPO.setExpiresIn(entity.getExpiresIn());
        randomPO.setId(entity.getId());
        randomPO.setTarget(entity.getTarget());
        randomPO.setModifiedTime(entity.getModifiedTime());
        randomPO.setType(entity.getType().getCode());
        randomPO.setValue(entity.getValue());
        randomPO.setWay(entity.getWay().getCode());
        randomPO.setStatus(ConvertUtils.mapIfNotNull(entity.getStatus(), Random.Status::getCode));
        return randomPO;
    }

    @Override
    public Random convert(RandomPO po) {
        if (po == null) {
            return null;
        }
        Random random = new Random();
        random.setCreatedTime(po.getCreatedTime());
        random.setExpiresIn(po.getExpiresIn());
        random.setId(po.getId());
        random.setModifiedTime(po.getModifiedTime());
        random.setStatus(Random.Status.getByCode(po.getStatus()));
        random.setTarget(po.getTarget());
        random.setType(Random.Type.getByCode(po.getType()));
        random.setValue(po.getValue());
        random.setWay(Random.Way.getByCode(po.getWay()));
        return random;
    }

}
