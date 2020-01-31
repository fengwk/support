package com.fengwk.support.spring.boot.starter.mysql.convention;

import org.apache.ibatis.session.RowBounds;

import com.fengwk.support.core.page.PageQuery;

/**
 * 
 * @author fengwk
 */
public class PageQueryAdapter extends RowBounds {

    public PageQueryAdapter(PageQuery pageQuery) {
        super(pageQuery.getOffset(), pageQuery.getLimit());
    }

}
