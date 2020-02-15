package com.fengwk.support.core.convention.query;

import java.util.ArrayList;
import java.util.List;

import com.fengwk.support.core.convention.query.Criterion.Condition;
import com.fengwk.support.core.util.bean.Property;

/**
 * 
 * @author fengwk
 */
public class Criteria<T> {

    List<Criterion<T>> chain = new ArrayList<>();
    AndOr andOr;
    
    public AndOr getAndOr() {
        return andOr;
    }

    public List<Criterion<T>> getChain() {
        return chain;
    }
    
    public Criteria<T> andIsNull(Property<T, ?> property) {
        this.chain.add(new Criterion<>(property, Condition.IS_NULL, AndOr.AND));
        return this;
    }
    
    public Criteria<T> andIsNotNull(Property<T, ?> property) {
        this.chain.add(new Criterion<>(property, Condition.IS_NOT_NULL, AndOr.AND));
        return this;
    }
    
    public <R> Criteria<T> andEqualTo(Property<T, R> property, R value) {
        this.chain.add(new Criterion<>(property, value, Condition.EQUAL_TO, AndOr.AND));
        return this;
    }
    
    public <R> Criteria<T> andNotEqualTo(Property<T, R> property, R value) {
        this.chain.add(new Criterion<>(property, value, Condition.NOT_EQUAL_TO, AndOr.AND));
        return this;
    }
    
    public <R> Criteria<T> andGreaterThan(Property<T, R> property, R value) {
        this.chain.add(new Criterion<>(property, value, Condition.GREATER_THAN, AndOr.AND));
        return this;
    }
    
    public <R> Criteria<T> andGreaterThanOrEqualTo(Property<T, R> property, R value) {
        this.chain.add(new Criterion<>(property, value, Condition.GREATER_THAN_OR_EQUAL_TO, AndOr.AND));
        return this;
    }
    
    public <R> Criteria<T> andLessThan(Property<T, R> property, R value) {
        this.chain.add(new Criterion<>(property, value, Condition.LESS_THAN, AndOr.AND));
        return this;
    }
    
    public <R> Criteria<T> andLessThanOrEqualTo(Property<T, R> property, R value) {
        this.chain.add(new Criterion<>(property, value, Condition.LESS_THAN_OR_EQUAL_TO, AndOr.AND));
        return this;
    }
    
    public Criteria<T> andIn(Property<T, Iterable<?>> property, Iterable<?> value) {
        this.chain.add(new Criterion<>(property, value, Condition.IN, AndOr.AND));
        return this;
    }
    
    public Criteria<T> andNotIn(Property<T, Iterable<?>> property, Iterable<?> value) {
        this.chain.add(new Criterion<>(property, value, Condition.NOT_IN, AndOr.AND));
        return this;
    }
    
    public <R> Criteria<T> andBetween(Property<T, R> property, R value, R secondValue) {
        this.chain.add(new Criterion<>(property, value, secondValue, Condition.BETWEEN, AndOr.AND));
        return this;
    }
    
    public <R> Criteria<T> andNotBetween(Property<T, R> property, R value, R secondValue) {
        this.chain.add(new Criterion<>(property, value, secondValue, Condition.NOT_BETWEEN, AndOr.AND));
        return this;
    }
    
    public Criteria<T> andLike(Property<T, String> property, String value) {
        this.chain.add(new Criterion<>(property, value, Condition.LIKE, AndOr.AND));
        return this;
    }
    
    public Criteria<T> andNotLike(Property<T, String> property, String value) {
        this.chain.add(new Criterion<>(property, value, Condition.NOT_LIKE, AndOr.AND));
        return this;
    }
    
    public Criteria<T> andLikePrefix(Property<T, String> property, String value) {
        this.chain.add(new Criterion<>(property, value, Condition.LIKE_PREFIX, AndOr.AND));
        return this;
    }
    
    public Criteria<T> andNotLikePrefix(Property<T, String> property, String value) {
        this.chain.add(new Criterion<>(property, value, Condition.NOT_LIKE_PREFIX, AndOr.AND));
        return this;
    }
    
    public Criteria<T> orIsNull(Property<T, ?> property) {
        this.chain.add(new Criterion<>(property, Condition.IS_NULL, AndOr.OR));
        return this;
    }
    
    public Criteria<T> orIsNotNull(Property<T, ?> property) {
        this.chain.add(new Criterion<>(property, Condition.IS_NOT_NULL, AndOr.OR));
        return this;
    }
    
    public <R> Criteria<T> orEqualTo(Property<T, R> property, R value) {
        this.chain.add(new Criterion<>(property, value, Condition.EQUAL_TO, AndOr.OR));
        return this;
    }
    
    public <R> Criteria<T> orNotEqualTo(Property<T, R> property, R value) {
        this.chain.add(new Criterion<>(property, value, Condition.NOT_EQUAL_TO, AndOr.OR));
        return this;
    }
    
    public <R> Criteria<T> orGreaterThan(Property<T, R> property, R value) {
        this.chain.add(new Criterion<>(property, value, Condition.GREATER_THAN, AndOr.OR));
        return this;
    }
    
    public <R> Criteria<T> orGreaterThanOrEqualTo(Property<T, R> property, R value) {
        this.chain.add(new Criterion<>(property, value, Condition.GREATER_THAN_OR_EQUAL_TO, AndOr.OR));
        return this;
    }
    
    public <R> Criteria<T> orLessThan(Property<T, R> property, R value) {
        this.chain.add(new Criterion<>(property, value, Condition.LESS_THAN, AndOr.OR));
        return this;
    }
    
    public <R> Criteria<T> orLessThanOrEqualTo(Property<T, R> property, R value) {
        this.chain.add(new Criterion<>(property, value, Condition.LESS_THAN_OR_EQUAL_TO, AndOr.OR));
        return this;
    }
    
    public Criteria<T> orIn(Property<T, Iterable<?>> property, Iterable<?> value) {
        this.chain.add(new Criterion<>(property, value, Condition.IN, AndOr.OR));
        return this;
    }
    
    public Criteria<T> orNotIn(Property<T, Iterable<?>> property, Iterable<?> value) {
        this.chain.add(new Criterion<>(property, value, Condition.NOT_IN, AndOr.OR));
        return this;
    }
    
    public <R> Criteria<T> orBetween(Property<T, R> property, R value, R secondValue) {
        this.chain.add(new Criterion<>(property, value, secondValue, Condition.BETWEEN, AndOr.OR));
        return this;
    }
    
    public <R> Criteria<T> orNotBetween(Property<T, R> property, R value, R secondValue) {
        this.chain.add(new Criterion<>(property, value, secondValue, Condition.NOT_BETWEEN, AndOr.OR));
        return this;
    }
    
    public Criteria<T> orLike(Property<T, String> property, String value) {
        this.chain.add(new Criterion<>(property, value, Condition.LIKE, AndOr.OR));
        return this;
    }
    
    public Criteria<T> orNotLike(Property<T, String> property, String value) {
        this.chain.add(new Criterion<>(property, value, Condition.NOT_LIKE, AndOr.OR));
        return this;
    }
    
    public Criteria<T> orLikePrefix(Property<T, String> property, String value) {
        this.chain.add(new Criterion<>(property, value, Condition.LIKE_PREFIX, AndOr.OR));
        return this;
    }
    
    public Criteria<T> orNotLikePrefix(Property<T, String> property, String value) {
        this.chain.add(new Criterion<>(property, value, Condition.NOT_LIKE_PREFIX, AndOr.OR));
        return this;
    }

}
