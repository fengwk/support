package com.fengwk.support.spring.boot.starter.mysql.convention;

import com.fengwk.support.core.util.bean.Property;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.SqlsCriteria;
import tk.mybatis.mapper.util.Sqls;

/**
 * 
 * @author fengwk
 */
public class ExampleBuilder {

    final Example.Builder builder;
    
    ExampleBuilder(Class<?> entityClass) {
        this.builder = Example.builder(entityClass); 
    }
    
    public ExampleBuilder distinct() {
        builder.distinct();
        return this;
    }

    public ExampleBuilder forUpdate() {
        builder.forUpdate();
        return this;
    }

    public ExampleBuilder selectDistinct(Property<?, ?>... propertys) {
        builder.selectDistinct(adaptProperties(propertys));
        return this;
    }

    public ExampleBuilder select(Property<?, ?>... propertys) {
        builder.select(adaptProperties(propertys));
        return this;
    }

    public ExampleBuilder notSelect(Property<?, ?>... propertys) {
        builder.notSelect(adaptProperties(propertys));
        return this;
    }

    public ExampleBuilder from(String tableName) {
        builder.from(tableName);
        return this;
    }

    public ExampleBuilder where(Sqls sqls) {
        builder.where(sqls);
        return this;
    }

    public ExampleBuilder where(SqlsCriteria sqls) {
        builder.where(sqls);
        return this;
    }

    public ExampleBuilder andWhere(Sqls sqls) {
        builder.andWhere(sqls);
        return this;
    }

    public ExampleBuilder andWhere(SqlsCriteria sqls) {
        builder.andWhere(sqls);
        return this;
    }

    public ExampleBuilder orWhere(Sqls sqls) {
        builder.orWhere(sqls);
        return this;
    }

    public ExampleBuilder orWhere(SqlsCriteria sqls) {
        builder.orWhere(sqls);
        return this;
    }

    public ExampleBuilder orderBy(Property<?, ?>... propertys) {
        builder.orderBy(adaptProperties(propertys));
        return this;
    }

    public ExampleBuilder orderByAsc(Property<?, ?>... propertys) {
        builder.orderByAsc(adaptProperties(propertys));
        return this;
    }

    public ExampleBuilder orderByDesc(Property<?, ?>... propertys) {
        builder.orderByDesc(adaptProperties(propertys));
        return this;
    }

    public ExampleBuilder setDistinct(boolean distinct) {
        builder.setDistinct(distinct);
        return this;
    }

    public ExampleBuilder setForUpdate(boolean forUpdate) {
        builder.setForUpdate(forUpdate);
        return this;
    }

    public ExampleBuilder setTableName(String tableName) {
        builder.setTableName(tableName);
        return this;
    }
    
    public Example build() {
        return builder.build();
    }
    
    private String[] adaptProperties(Property<?, ?>... propertys) {
        String[] propertyNames = new String[propertys.length];
        for (int i = 0; i < propertys.length; i++) {
            propertyNames[i] = propertys[i].name();
        }
        return propertyNames;
    }
    
}
