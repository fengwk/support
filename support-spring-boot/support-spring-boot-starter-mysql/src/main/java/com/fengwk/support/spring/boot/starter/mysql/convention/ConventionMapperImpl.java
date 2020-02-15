package com.fengwk.support.spring.boot.starter.mysql.convention;

import java.time.LocalDateTime;
import java.util.List;

import com.fengwk.support.core.convention.exception.Preconditions;
import com.fengwk.support.core.convention.page.Page;
import com.fengwk.support.core.convention.page.PageQuery;
import com.fengwk.support.core.domain.model.BasicEntity;
import com.fengwk.support.core.util.ConvertUtils;
import com.fengwk.support.spring.boot.starter.mysql.BasicConverter;
import com.fengwk.support.spring.boot.starter.mysql.BasicMapper;
import com.fengwk.support.spring.boot.starter.mysql.BasicPO;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

/**
 * 
 * @author fengwk
 */
public class ConventionMapperImpl<E extends BasicEntity<I>, P extends BasicPO<I>, I> implements ConventionMapper<E, P, I> {

    final BasicMapper<P, I> basicMapper;
    final BasicConverter<E, P, I> basicConverter;
    final Class<P> poClass;
    
    ConventionMapperImpl(BasicMapper<P, I> basicMapper, BasicConverter<E, P, I> basicConverter, Class<P> poClass) {
        Preconditions.notNull(basicMapper, "BasicMapper cannot be null.");
        Preconditions.notNull(basicConverter, "BasicConverter cannot be null.");
        Preconditions.notNull(poClass, "POClass cannot be null.");
        this.basicMapper = basicMapper;
        this.basicConverter = basicConverter;
        this.poClass = poClass;
    }
    
    @Override
    public List<E> listAll() {
        return ConvertUtils.mapToList(basicMapper.selectAll(), basicConverter::convert);
    }

    @Override
    public E getById(I id) {
        return basicConverter.convert(basicMapper.selectByPrimaryKey(id));
    }

    @Override
    public List<E> listByIds(Iterable<I> ids) {
        Example example = new ExampleBuilder(poClass).andWhere(WeekendSqls.<P>custom().andIn(P::getId, ids)).build();
        return listByExample(example);
    }

    @Override
    public boolean existsById(I id) {
        return basicMapper.existsWithPrimaryKey(id);
    }

    @Override
    public int insert(E entity) {
        if (entity.getCreatedTime() == null) {
            entity.setCreatedTime(LocalDateTime.now());
        }
        if (entity.getModifiedTime() == null) {
            entity.setModifiedTime(LocalDateTime.now());
        }
        P po = basicConverter.convert(entity);
        int ret = basicMapper.insert(po);
        entity.setId(po.getId());
        return ret;
    }

    @Override
    public int insertSelective(E entity) {
        if (entity.getCreatedTime() == null) {
            entity.setCreatedTime(LocalDateTime.now());
        }
        if (entity.getModifiedTime() == null) {
            entity.setModifiedTime(LocalDateTime.now());
        }
        P po = basicConverter.convert(entity);
        int ret = basicMapper.insertSelective(po);
        entity.setId(po.getId());
        return ret;
    }

    @Override
    public int updateById(E entity) {
        entity.setModifiedTime(LocalDateTime.now());
        P po = basicConverter.convert(entity);
        return basicMapper.updateByPrimaryKey(po);
    }

    @Override
    public int updateByIdSelective(E entity) {
        entity.setModifiedTime(LocalDateTime.now());
        P po = basicConverter.convert(entity);
        return basicMapper.updateByPrimaryKeySelective(po);
    }

    @Override
    public int deleteById(I id) {
        return basicMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<E> listByExample(Example example) {
        return ConvertUtils.mapToList(basicMapper.selectByExample(example), basicConverter::convert);
    }

    @Override
    public E getByExample(Example example) {
        return basicConverter.convert(basicMapper.selectOneByExample(example));
    }

    @Override
    public int countByExample(Example example) {
        return basicMapper.selectCountByExample(example);
    }

    @Override
    public int deleteByExample(Example example) {
        return basicMapper.deleteByExample(example);
    }

    @Override
    public int updateByExample(E entity, Example example) {
        entity.setModifiedTime(LocalDateTime.now());
        P po = basicConverter.convert(entity);
        return basicMapper.updateByExample(po, example);
    }

    @Override
    public int updateByExampleSelective(E entity, Example example) {
        entity.setModifiedTime(LocalDateTime.now());
        P po = basicConverter.convert(entity);
        return basicMapper.updateByExampleSelective(po, example);
    }

    @Override
    public Page<E> page(PageQuery pageQuery) {
        return page(pageQuery, true);
    }

    @Override
    public Page<E> page(PageQuery pageQuery, boolean isCountTotal) {
        Example example = new ExampleBuilder(poClass).build();
        return pageByExample(example, pageQuery, isCountTotal);
    }

    @Override
    public Page<E> pageByExample(Example example, PageQuery pageQuery) {
        return pageByExample(example, pageQuery, true);
    }

    @Override
    public Page<E> pageByExample(Example example, PageQuery pageQuery, boolean isCountTotal) {
        List<P> results = basicMapper.selectByExampleAndRowBounds(example, new PageQueryAdapter(pageQuery));
        Page<E> page = pageQuery.page(results, basicConverter::convert);
        if (isCountTotal) {
            int total = basicMapper.selectCountByExample(example);
            page.setTotal(total);
        }
        return page;
    }

}
