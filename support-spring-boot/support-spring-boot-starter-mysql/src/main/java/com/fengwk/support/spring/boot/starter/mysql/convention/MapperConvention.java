package com.fengwk.support.spring.boot.starter.mysql.convention;

import java.time.LocalDateTime;
import java.util.List;

import com.fengwk.support.core.domain.model.BasicEntity;
import com.fengwk.support.core.exception.Preconditions;
import com.fengwk.support.core.page.Page;
import com.fengwk.support.core.page.PageQuery;
import com.fengwk.support.core.util.ConvertUtils;
import com.fengwk.support.spring.boot.starter.mysql.BasicMapper;
import com.fengwk.support.spring.boot.starter.mysql.BasicPO;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

/**
 * 
 * @author fengwk
 */
public class MapperConvention<E extends BasicEntity<I>, P extends BasicPO<I>, I> {

    final BasicMapper<P, I> mapper;
    final Converter<E, P, I> converter;
    final Class<P> poClass;
    
    MapperConvention(BasicMapper<P, I> mapper, Converter<E, P, I> converter, Class<P> poClass) {
        Preconditions.notNull(mapper, "Mapper cannot be null.");
        Preconditions.notNull(converter, "Converter cannot be null.");
        this.mapper = mapper;
        this.converter = converter;
        this.poClass = poClass;
    }

    public List<E> listAll() {
        return ConvertUtils.mapToList(mapper.selectAll(), converter::convert);
    }

    public E getById(I id) {
        return converter.convert(mapper.selectByPrimaryKey(id));
    }
    
    public List<E> listById(Iterable<I> ids) {
        Example example = new ExampleBuilder(poClass).andWhere(WeekendSqls.<P>custom().andIn(P::getId, ids)).build();
        return listByExample(example);
    }

    public boolean existsById(I id) {
        return mapper.existsWithPrimaryKey(id);
    }

    public int insert(E entity) {
        if (entity.getCreatedTime() == null) {
            entity.setCreatedTime(LocalDateTime.now());
        }
        if (entity.getModifiedTime() == null) {
            entity.setModifiedTime(LocalDateTime.now());
        }
        P po = converter.convert(entity);
        int ret = mapper.insert(po);
        entity.setId(po.getId());
        return ret;
    }

    public int insertSelective(E entity) {
        if (entity.getCreatedTime() == null) {
            entity.setCreatedTime(LocalDateTime.now());
        }
        if (entity.getModifiedTime() == null) {
            entity.setModifiedTime(LocalDateTime.now());
        }
        P po = converter.convert(entity);
        int ret = mapper.insertSelective(po);
        entity.setId(po.getId());
        return ret;
    }

    public int updateById(E entity) {
        entity.setModifiedTime(LocalDateTime.now());
        P po = converter.convert(entity);
        return mapper.updateByPrimaryKey(po);
    }

    public int updateByIdSelective(E entity) {
        entity.setModifiedTime(LocalDateTime.now());
        P po = converter.convert(entity);
        return mapper.updateByPrimaryKeySelective(po);
    }

    public int deleteById(I id) {
        return mapper.deleteByPrimaryKey(id);
    }

    public List<E> listByExample(Example example) {
        return ConvertUtils.mapToList(mapper.selectByExample(example), converter::convert);
    }

    public E getByExample(Example example) {
        return converter.convert(mapper.selectOneByExample(example));
    }

    public int countByExample(Example example) {
        return mapper.selectCountByExample(example);
    }

    public int deleteByExample(Example example) {
        return mapper.deleteByExample(example);
    }

    public int updateByExample(E entity, Example example) {
        entity.setModifiedTime(LocalDateTime.now());
        P po = converter.convert(entity);
        return mapper.updateByExample(po, example);
    }

    public int updateByExampleSelective(E entity, Example example) {
        entity.setModifiedTime(LocalDateTime.now());
        P po = converter.convert(entity);
        return mapper.updateByExampleSelective(po, example);
    }
    
    public Page<E> page(PageQuery pageQuery) {
        Example example = new ExampleBuilder(poClass).build();
        return pageByExample(example, pageQuery);
    }

    public Page<E> pageByExample(Example example, PageQuery pageQuery) {
        return pageQuery.page(mapper.selectByExampleAndRowBounds(example, new PageQueryAdapter(pageQuery)), converter::convert);
    }
    
}
