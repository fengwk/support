package com.fengwk.support.core.page;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fengwk.support.core.exception.Preconditions;

/**
 * 
 * @author fengwk
 */
public class Page<T> implements Serializable {

    private static final long serialVersionUID = 2292586620697587338L;

    private Integer pageNumber;

    private Integer pageSize;

    private Boolean hasPrev;

    private Boolean hasNext;

    private List<T> results;

    private Integer total;

    public Integer getPageNumber() {
        return pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Boolean getHasPrev() {
        return hasPrev;
    }

    public Boolean getHasNext() {
        return hasNext;
    }

    public List<T> getResults() {
        return results;
    }

    public Integer getTotal() {
        return total;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setHasPrev(Boolean hasPrev) {
        this.hasPrev = hasPrev;
    }

    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public <S> Page<S> map(Function<T, S> converter) {
        Preconditions.notNull(hasNext, "HasNext can not be null");
        Preconditions.notNull(hasPrev, "HasPrev can not be null");
        Preconditions.notNull(pageNumber, "PageNumber can not be null");
        Preconditions.notNull(pageSize, "PageSize can not be null");
        Preconditions.notNull(results, "Results can not be null");
        Page<S> page = new Page<>();
        page.setHasNext(hasNext);
        page.setHasPrev(hasPrev);
        page.setPageNumber(pageNumber);
        page.setPageSize(pageSize);
        page.setResults(results.stream().map(converter).collect(Collectors.toList()));
        page.setTotal(total);
        return page;
    }

}
