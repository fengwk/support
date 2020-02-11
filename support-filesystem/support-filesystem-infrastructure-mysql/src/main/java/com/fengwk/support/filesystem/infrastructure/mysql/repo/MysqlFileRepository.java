package com.fengwk.support.filesystem.infrastructure.mysql.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fengwk.support.filesystem.domain.model.File;
import com.fengwk.support.filesystem.domain.repo.FileRepository;
import com.fengwk.support.filesystem.infrastructure.mysql.FilesystemMysqlRepository;
import com.fengwk.support.filesystem.infrastructure.mysql.converter.FileConverter;
import com.fengwk.support.filesystem.infrastructure.mysql.mapper.FileMapper;
import com.fengwk.support.filesystem.infrastructure.mysql.model.FilePO;
import tk.mybatis.mapper.entity.Example;

/**
 * 
 * @author fengwk
 */
@Repository
public class MysqlFileRepository extends FilesystemMysqlRepository<File, FilePO> implements FileRepository {

    @Autowired
    public MysqlFileRepository(FileMapper fileMapper, FileConverter fileConverter) {
        super(fileMapper, fileConverter);
    }

    @Override
    public void add(File file) {
        mapper().insert(file);
    }

    @Override
    public File getByAccessId(String accessId) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andEqualTo(FilePO::getAccessId, accessId))
                .build();
        return mapper().getByExample(example);
    }
    
    @Override
    public File getByDigest(String digest) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andEqualTo(FilePO::getDigest, digest))
                .build();
        return mapper().getByExample(example);
    }

}
