package com.fengwk.support.core.convention.page;

import com.fengwk.support.core.convention.exception.Preconditions;

/**
 * 
 * @author fengwk
 */
public class PageQueryValidator {

    private PageQueryValidator() {}
    
    public static void check(PageQueryDTO pageQueryDTO) {
        Preconditions.notNull(pageQueryDTO, "分页器不能为空");
        Preconditions.notNull(pageQueryDTO.getPageNumber(), "分页器页码不能为空");
        Preconditions.notNull(pageQueryDTO.getPageSize(), "分页器尺寸不能为空");
    }
    
    public static void check(PageQuery pageQuery) {
        Preconditions.notNull(pageQuery, "分页器不能为空");
        Preconditions.notNull(pageQuery.getPageNumber(), "分页器页码不能为空");
        Preconditions.notNull(pageQuery.getPageSize(), "分页器尺寸不能为空");
    }
    
}
