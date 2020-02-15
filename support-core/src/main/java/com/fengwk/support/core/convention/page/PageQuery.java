package com.fengwk.support.core.convention.page;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * 分页查询器
 * 
 * @author fengwk
 */
public class PageQuery implements Serializable {

    private static final long serialVersionUID = 844689393088787301L;

    /**
     * 最小页码
     */
    private static final int MINIMUM_PAGE_NUMBER = 1;
    
    /**
     * 最小分页尺寸
     */
    private static final int MINIMUM_PAGE_SIZE = 0;
    
    /**
     * 最大分页尺寸
     */
    private static final int MAXIMUM_PAGE_SIZE = 1000;
    
    /**
     * 页码
     */
    private Integer pageNumber;
    
    /**
     * 分页尺寸
     */
    private Integer pageSize;
    
    /**
     * 分页偏移量
     */
    private Integer offset;
    
    /**
     * 分页限制量
     */
    private Integer limit;
    
    public PageQuery() {}
    
    public PageQuery(Integer pageNumber, Integer pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        computePageParams();
    }
    
    public PageQuery(PageQueryDTO pageQueryDTO) {
        this.pageNumber = pageQueryDTO.getPageNumber();
        this.pageSize = pageQueryDTO.getPageSize();
        computePageParams();
    }
    
    /**
     * 获取分页结果
     * 
     * @param results
     * @return
     */
    public <T> Page<T> page(List<T> results) {
        if (results == null) {
            results = Collections.emptyList();
        }
        
        boolean hasNext = false;
        if (results.size() == getLimit()) {
            hasNext = true;
            results.remove(results.size() - 1);
        }
        
        Page<T> page = new Page<>();
        page.setPageNumber(pageNumber);
        page.setPageSize(pageSize);
        page.setHasPrev(pageNumber > MINIMUM_PAGE_NUMBER);
        page.setHasNext(hasNext);
        page.setResults(results);
        return page;
    }
    
    /**
     * 获取分页结果并转换
     * 
     * @param results
     * @param converter
     * @return
     */
    public <T, S> Page<S> page(List<T> results, Function<T, S> converter) {
        return page(results).map(converter);
    }

    /**
     * 获取页码
     * 
     * @return
     */
    public Integer getPageNumber() {
        return pageNumber;
    }

    /**
     * 设置页码
     * 
     * @param pageNumber
     */
    public void setPageNumber(Integer pageNumber) {
        clearPageParams();
        this.pageNumber = pageNumber;
    }

    /**
     * 获取分页尺寸
     * 
     * @return
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * 设置分页尺寸
     * 
     * @param pageSize
     */
    public void setPageSize(Integer pageSize) {
        clearPageParams();
        this.pageSize = pageSize;
    }
    
    /**
     * 分页查询偏移量
     * 
     * @return
     */
    public Integer getOffset() {
        computePageParams();
        return offset;
    }

    /**
     * 分页查询限制量
     * 
     * @return
     */
    public Integer getLimit() {
        computePageParams();
        return limit;
    }

    private void clearPageParams() {
        offset = null;
        limit = null;
    }
    
    private void computePageParams() {
        if (offset != null && limit != null) {
            return;
        }
        if (pageNumber == null) {
            throw new NullPointerException("pageNumber == null");
        }
        if (pageSize == null) {
            throw new NullPointerException("pageSize == null");
        }
        if (pageNumber < MINIMUM_PAGE_NUMBER) {
            throw new IllegalArgumentException("pageNumber < MINIMUM_PAGE_NUMBER");
        }
        if (pageSize < MINIMUM_PAGE_SIZE) {
            throw new IllegalArgumentException("pageSize < MINIMUM_PAGE_SIZE");
        }
        if (pageSize > MAXIMUM_PAGE_SIZE) {
            throw new IllegalArgumentException("pageSize > MAXIMUM_PAGE_SIZE");
        }
        offset = (pageNumber - 1) * pageSize;
        limit = pageSize + 1;
    }

}
