package com.fengwk.support.spring.boot.starter.mysql.convention;

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

    public ExampleBuilder selectDistinct(Field... fields) {
        builder.selectDistinct(convertToProperties(fields));
        return this;
    }

    public ExampleBuilder select(Field... fields) {
        builder.select(convertToProperties(fields));
        return this;
    }

    public ExampleBuilder notSelect(Field... fields) {
        builder.notSelect(convertToProperties(fields));
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

    public ExampleBuilder orderBy(Field... fields) {
        builder.orderBy(convertToProperties(fields));
        return this;
    }

    public ExampleBuilder orderByAsc(Field... fields) {
        builder.orderByAsc(convertToProperties(fields));
        return this;
    }

    public ExampleBuilder orderByDesc(Field... fields) {
        builder.orderByDesc(convertToProperties(fields));
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
    
    private String[] convertToProperties(Field... fields) {
        String[] properties = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            properties[i] = fields[i].name();
        }
        return properties;
    }
    
}
