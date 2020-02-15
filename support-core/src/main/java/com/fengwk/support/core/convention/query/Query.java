package com.fengwk.support.core.convention.query;

import java.util.ArrayList;
import java.util.List;

import com.fengwk.support.core.convention.query.OrderBy.Direction;
import com.fengwk.support.core.util.bean.Property;

import lombok.Data;

/**
 * 
 * @author fengwk
 */
@Data
public class Query<T> {

    private List<Criteria<T>> chain = new ArrayList<>();
    private List<OrderBy<T>> orderBys = new ArrayList<>();
    
    public Query<T> and(Criteria<T> criteria) {
        criteria.andOr = AndOr.AND;
        this.chain.add(criteria);
        return this;
    }
    
    public Query<T> or(Criteria<T> criteria) {
        criteria.andOr = AndOr.OR;
        this.chain.add(criteria);
        return this;
    }
    
    public Query<T> orderByAsc(Property<T, ?> property) {
        this.orderBys.add(new OrderBy<>(property, Direction.ASC));
        return this;
    }
    
    public Query<T> orderByDesc(Property<T, ?> property) {
        this.orderBys.add(new OrderBy<>(property, Direction.DESC));
        return this;
    }
    
}
