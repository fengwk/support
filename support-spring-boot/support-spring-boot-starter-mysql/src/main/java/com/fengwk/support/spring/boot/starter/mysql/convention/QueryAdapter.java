package com.fengwk.support.spring.boot.starter.mysql.convention;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.fengwk.support.core.bean.Property;
import com.fengwk.support.core.query.Criteria;
import com.fengwk.support.core.query.Criterion;
import com.fengwk.support.core.query.OrderBy;
import com.fengwk.support.core.query.Query;
import com.fengwk.support.core.sql.SqlInjectionErrorCode;
import com.fengwk.support.core.sql.SqlInjectionException;
import com.fengwk.support.core.util.ValidationUtils;
import com.fengwk.support.domain.model.BasicEntity;
import com.fengwk.support.spring.boot.starter.mysql.BasicPO;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

/**
 * 
 * @author fengwk
 */
class QueryAdapter<E extends BasicEntity<I>, P extends BasicPO<I>, I> {

    private final Query<E> query;
    private final PropertyMapper propertyMapper;
    private final Class<P> poClass;
    
    QueryAdapter(Query<E> query, PropertyMapper propertyMapper, Class<P> poClass) {
        this.query = query;
        this.propertyMapper = propertyMapper;
        this.poClass = poClass;
    }
    
    Example adapt() {
        Example.Builder builder = Example.builder(poClass);
        adaptCriteria(builder, query.getChain());
        adaptOrderBy(builder, query.getOrderBys());
        return builder.build();
    }
    
    private void adaptCriteria(Example.Builder builder, List<Criteria<E>> chain) {
        if (CollectionUtils.isEmpty(chain)) {
            return;
        }
        
        for (Criteria<E> criteria : chain) {
            switch (criteria.getAndOr()) {
            case AND:
                if (CollectionUtils.isNotEmpty(criteria.getChain())) {
                    builder.andWhere(buildSqls(criteria.getChain()));
                }
                break;
            case OR:
                if (CollectionUtils.isNotEmpty(criteria.getChain())) {
                    builder.orWhere(buildSqls(criteria.getChain()));
                }
                break;
            }
        }
    }
    
    private void adaptOrderBy(Example.Builder builder, List<OrderBy<E>> orderBys) {
        if (CollectionUtils.isEmpty(orderBys)) {
            return;
        }
        
        for (OrderBy<E> orderBy : orderBys) {
            switch (orderBy.getDirection()) {
            case ASC:
                builder.orderByAsc(adaptProperty(orderBy.getProperty()));
                break;
            case DESC:
                builder.orderByDesc(adaptProperty(orderBy.getProperty()));
                break;
            }
        }
    }
    
    private Sqls buildSqls(List<Criterion<E>> chain) {
        Sqls sqls = Sqls.custom();
        
        for (Criterion<E> criterion : chain) {
            Property<?, ?> targetProperty = propertyMapper.getTarget(criterion.getProperty());

            switch (criterion.getCondition()) {
            case IS_NULL:
                switch (criterion.getAndOr()) {
                case AND:
                    sqls.andIsNull(adaptProperty(targetProperty));
                    break;
                case OR:
                    sqls.orIsNull(adaptProperty(targetProperty));
                    break;
                }
                break;
                
            case IS_NOT_NULL:
                switch (criterion.getAndOr()) {
                case AND:
                    sqls.andIsNotNull(adaptProperty(targetProperty));
                    break;
                case OR:
                    sqls.orIsNotNull(adaptProperty(targetProperty));
                    break;
                }
                break;
                            
            case EQUAL_TO:
                switch (criterion.getAndOr()) {
                case AND:
                    sqls.andEqualTo(adaptProperty(targetProperty), criterion.getValue());
                    break;
                case OR:
                    sqls.orEqualTo(adaptProperty(targetProperty), criterion.getValue());
                    break;
                }
                break;
                
            case NOT_EQUAL_TO:
                switch (criterion.getAndOr()) {
                case AND:
                    sqls.andNotEqualTo(adaptProperty(targetProperty), criterion.getValue());
                    break;
                case OR:
                    sqls.orNotEqualTo(adaptProperty(targetProperty), criterion.getValue());
                    break;
                }
                break;
                
            case GREATER_THAN:
                switch (criterion.getAndOr()) {
                case AND:
                    sqls.andGreaterThan(adaptProperty(targetProperty), criterion.getValue());
                    break;
                case OR:
                    sqls.orGreaterThan(adaptProperty(targetProperty), criterion.getValue());
                    break;
                }
                break;
                
            case GREATER_THAN_OR_EQUAL_TO:
                switch (criterion.getAndOr()) {
                case AND:
                    sqls.andGreaterThanOrEqualTo(adaptProperty(targetProperty), criterion.getValue());
                    break;
                case OR:
                    sqls.orGreaterThanOrEqualTo(adaptProperty(targetProperty), criterion.getValue());
                    break;
                }
                break;
                
            case LESS_THAN:
                switch (criterion.getAndOr()) {
                case AND:
                    sqls.andLessThan(adaptProperty(targetProperty), criterion.getValue());
                    break;
                case OR:
                    sqls.orLessThan(adaptProperty(targetProperty), criterion.getValue());
                    break;
                }
                break;
                
            case LESS_THAN_OR_EQUAL_TO:
                switch (criterion.getAndOr()) {
                case AND:
                    sqls.andLessThanOrEqualTo(adaptProperty(targetProperty), criterion.getValue());
                    break;
                case OR:
                    sqls.orLessThanOrEqualTo(adaptProperty(targetProperty), criterion.getValue());
                    break;
                }
                break;
                
            case IN:
                switch (criterion.getAndOr()) {
                case AND:
                    sqls.andIn(adaptProperty(targetProperty), (Iterable<?>) criterion.getValue());
                    break;
                case OR:
                    sqls.orIn(adaptProperty(targetProperty), (Iterable<?>) criterion.getValue());
                    break;
                }
                break;
                
            case NOT_IN:
                switch (criterion.getAndOr()) {
                case AND:
                    sqls.andNotIn(adaptProperty(targetProperty), (Iterable<?>) criterion.getValue());
                    break;
                case OR:
                    sqls.orNotIn(adaptProperty(targetProperty), (Iterable<?>) criterion.getValue());
                    break;
                }
                break;
                
            case BETWEEN:
                switch (criterion.getAndOr()) {
                case AND:
                    sqls.andBetween(adaptProperty(targetProperty), criterion.getValue(), criterion.getSecondValue());
                    break;
                case OR:
                    sqls.orBetween(adaptProperty(targetProperty), criterion.getValue(), criterion.getSecondValue());
                    break;
                }
                break;
                
            case NOT_BETWEEN:
                switch (criterion.getAndOr()) {
                case AND:
                    sqls.andNotBetween(adaptProperty(targetProperty), criterion.getValue(), criterion.getSecondValue());
                    break;
                case OR:
                    sqls.orNotBetween(adaptProperty(targetProperty), criterion.getValue(), criterion.getSecondValue());
                    break;
                }
                break;
                
            case LIKE:
                switch (criterion.getAndOr()) {
                case AND:
                    sqls.andLike(adaptProperty(targetProperty), adaptLike((String) criterion.getValue()));
                    break;
                case OR:
                    sqls.orLike(adaptProperty(targetProperty), adaptLike((String) criterion.getValue()));
                    break;
                }
                break;
                
            case NOT_LIKE:
                switch (criterion.getAndOr()) {
                case AND:
                    sqls.andNotLike(adaptProperty(targetProperty), adaptLike((String) criterion.getValue()));
                    break;
                case OR:
                    sqls.orNotLike(adaptProperty(targetProperty), adaptLike((String) criterion.getValue()));
                    break;
                }
                break;
                
            case LIKE_PREFIX:
                switch (criterion.getAndOr()) {
                case AND:
                    sqls.andLike(adaptProperty(targetProperty), adaptLikePrefix((String) criterion.getValue()));
                    break;
                case OR:
                    sqls.orLike(adaptProperty(targetProperty), adaptLikePrefix((String) criterion.getValue()));
                    break;
                }
                break;
                
            case NOT_LIKE_PREFIX:
                switch (criterion.getAndOr()) {
                case AND:
                    sqls.andNotLike(adaptProperty(targetProperty), adaptLikePrefix((String) criterion.getValue()));
                    break;
                case OR:
                    sqls.orNotLike(adaptProperty(targetProperty), adaptLikePrefix((String) criterion.getValue()));
                    break;
                }
                break;
            }
        }
        
        return sqls;
    }
    
    private String adaptProperty(Property<?, ?> property) {
        return property.getName();
    }
    
    private String adaptLike(String value) {
        if (value == null) {
            return null;
        }
        detectLike(value);
        return "%" + value + "%";
    }
    
    private String adaptLikePrefix(String prefixValue) {
        if (prefixValue == null) {
            return null;
        }
        detectLike(prefixValue);
        return prefixValue + "%";
    }
    
    private void detectLike(String value) {
        if (!ValidationUtils.isLegalLike(value)) {
            throw new SqlInjectionException(new SqlInjectionErrorCode(value));
        }
    }
    
}
