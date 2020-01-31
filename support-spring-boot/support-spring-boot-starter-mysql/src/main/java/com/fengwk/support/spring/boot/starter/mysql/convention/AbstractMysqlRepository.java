package com.fengwk.support.spring.boot.starter.mysql.convention;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.fengwk.support.core.domain.model.BasicEntity;
import com.fengwk.support.core.reflect.GenericUtils;
import com.fengwk.support.spring.boot.starter.mysql.BasicMapper;
import com.fengwk.support.spring.boot.starter.mysql.BasicPO;

import tk.mybatis.mapper.weekend.Fn;
import tk.mybatis.mapper.weekend.WeekendSqls;

/**
 * 
 * @author fengwk
 */
public abstract class AbstractMysqlRepository<E extends BasicEntity<I>, P extends BasicPO<I>, I> {
    
    final Class<P> poClass;
    
    @SuppressWarnings("unchecked")
    protected AbstractMysqlRepository() {
        Type poType = GenericUtils.findNearestVarType(getClass(), AbstractMysqlRepository.class, 1);
        if (poType instanceof Class) {
            this.poClass = (Class<P>) poType;
        } else if (poType instanceof ParameterizedType) {
            this.poClass = (Class<P>) ((ParameterizedType) poType).getRawType();
        } else {
            throw new IllegalStateException("Cannot found poClass in " + getClass() + ".");
        }
    }
    
    protected abstract BasicMapper<P, I> mapper();
    
    protected abstract Converter<E, P, I> converter();
    
    protected MapperConvention<E, P, I> mapperConvention() {
        return new MapperConvention<>(mapper(), converter(), poClass);
    }
    
    protected ExampleBuilder exampleBuilder() {
        return new ExampleBuilder(poClass);
    }
    
    protected WeekendSqls<P> weekendSqls() {
        return WeekendSqls.<P>custom();
    }
    
    protected Field field(Fn<P, ?> fn) {
        return new Field(fn);
    }
    
}
