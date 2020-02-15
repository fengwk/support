package com.fengwk.support.spring.boot.starter.mysql.convention;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.fengwk.support.core.convention.exception.Preconditions;
import com.fengwk.support.core.domain.model.BasicEntity;
import com.fengwk.support.core.util.bean.Property;
import com.fengwk.support.core.util.bean.Property.Fn;
import com.fengwk.support.core.util.reflect.GenericUtils;
import com.fengwk.support.spring.boot.starter.mysql.BasicConverter;
import com.fengwk.support.spring.boot.starter.mysql.BasicMapper;
import com.fengwk.support.spring.boot.starter.mysql.BasicPO;

import tk.mybatis.mapper.weekend.WeekendSqls;

/**
 * 
 * @author fengwk
 */
public class ConventionRepository<E extends BasicEntity<I>, P extends BasicPO<I>, I> {
    
    protected final BasicMapper<P, I> basicMapper;
    protected final BasicConverter<E, P, I> basicConverter;
    protected final Class<P> poClass;
    
    public ConventionRepository(BasicMapper<P, I> basicMapper, BasicConverter<E, P, I> basicConverter) {
        Preconditions.notNull(basicMapper, "BasicMapper cannot be null.");
        Preconditions.notNull(basicConverter, "BasicConverter cannot be null.");
        this.basicMapper = basicMapper;
        this.basicConverter = basicConverter;
        this.poClass = findPOClass();
    }
    
    private Class<P> findPOClass() {
        Type poType = GenericUtils.findNearestVarType(getClass(), ConventionRepository.class, 1);
        if (poType instanceof Class) {
            @SuppressWarnings("unchecked")
            Class<P> poClass = (Class<P>) poType;
            return poClass;
        } else if (poType instanceof ParameterizedType) {
            @SuppressWarnings("unchecked")
            Class<P> poClass = (Class<P>) ((ParameterizedType) poType).getRawType();
            return poClass;
        } else {
            throw new IllegalStateException("Cannot found poClass in " + getClass() + ".");
        }
    }
    
    protected ConventionMapper<E, P, I> mapper() {
        return new ConventionMapperImpl<>(basicMapper, basicConverter, poClass);
    }
    
    protected ExampleBuilder exampleBuilder() {
        return new ExampleBuilder(poClass);
    }
    
    protected WeekendSqls<P> weekendSqls() {
        return WeekendSqls.<P>custom();
    }
    
    protected Property<P, ?> property(Fn<P, ?> fn) {
        return Property.of(fn);
    }
    
}
