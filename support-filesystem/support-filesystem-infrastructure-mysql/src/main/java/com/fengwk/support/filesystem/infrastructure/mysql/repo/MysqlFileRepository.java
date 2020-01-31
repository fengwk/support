package com.fengwk.support.filesystem.infrastructure.mysql.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import com.fengwk.support.filesystem.domain.model.File;
import com.fengwk.support.filesystem.domain.repo.FileRepository;
import com.fengwk.support.filesystem.infrastructure.mysql.FilesystemMysqlRepository;
import com.fengwk.support.filesystem.infrastructure.mysql.converter.FileConverter;
import com.fengwk.support.filesystem.infrastructure.mysql.mapper.FileMapper;
import com.fengwk.support.filesystem.infrastructure.mysql.model.FilePO;
import com.fengwk.support.spring.boot.starter.mysql.BasicMapper;
import com.fengwk.support.spring.boot.starter.mysql.convention.Converter;

import tk.mybatis.mapper.entity.Example;

/**
 * 
 * @author fengwk
 */
@Validated
@Repository
public class MysqlFileRepository extends FilesystemMysqlRepository<File, FilePO> implements FileRepository {

    @Autowired
    volatile FileMapper fileMapper;
    
    @Autowired
    volatile FileConverter fileConverter;
    
    @Override
    protected BasicMapper<FilePO, Long> mapper() {
        return fileMapper;
    }

    @Override
    protected Converter<File, FilePO, Long> converter() {
        return fileConverter;
    }
    
    @Override
    public void add(File file) {
        mapperConvention().insert(file);
    }

    @Override
    public File getByAccessId(String accessId) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andEqualTo(FilePO::getAccessId, accessId))
                .build();
        return mapperConvention().getByExample(example);
    }
    
    @Override
    public File getByDigest(String digest) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andEqualTo(FilePO::getDigest, digest))
                .build();
        return mapperConvention().getByExample(example);
    }

}
