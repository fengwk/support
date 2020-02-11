package com.fengwk.support.spring.boot.starter.mysql.convention;

import javax.annotation.PostConstruct;

import com.fengwk.support.core.bean.Property;
import com.fengwk.support.domain.model.BasicEntity;
import com.fengwk.support.spring.boot.starter.mysql.BasicConverter;
import com.fengwk.support.spring.boot.starter.mysql.BasicMapper;
import com.fengwk.support.spring.boot.starter.mysql.BasicPO;

/**
 * 
 * @author fengwk
 */
public class QueryConventionRepository<E extends BasicEntity<I>, P extends BasicPO<I>, I> extends ConventionRepository<E, P, I> {

    protected final PropertyMapper propertyMapper = new PropertyMapper();
    
    @PostConstruct
    public void init() {
        register(propertyMapper);
    }
    
    public QueryConventionRepository(BasicMapper<P, I> basicMapper, BasicConverter<E, P, I> converter) {
        super(basicMapper, converter);
    }
    
    protected void register(PropertyMapper propertyMapper) {
        propertyMapper.register(Property.of(E::getId), Property.of(P::getId));
        propertyMapper.register(Property.of(E::getCreatedTime), Property.of(P::getCreatedTime));
        propertyMapper.register(Property.of(E::getModifiedTime), Property.of(P::getModifiedTime));
    }
    
    @Override
    protected QueryConventionMapper<E, P, I> mapper() {
        ConventionMapper<E, P, I> conventionMapper = new ConventionMapperImpl<>(super.basicMapper, super.basicConverter, super.poClass);
        return new QueryConventionMapperImpl<>(conventionMapper, propertyMapper, poClass);
    }
    
}
