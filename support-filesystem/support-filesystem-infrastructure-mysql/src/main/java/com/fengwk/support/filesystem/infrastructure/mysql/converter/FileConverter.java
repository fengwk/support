package com.fengwk.support.filesystem.infrastructure.mysql.converter;

import org.springframework.stereotype.Component;

import com.fengwk.support.filesystem.domain.model.File;
import com.fengwk.support.filesystem.infrastructure.mysql.FilesystemConverter;
import com.fengwk.support.filesystem.infrastructure.mysql.model.FilePO;

/**
 * 
 * @author fengwk
 */
@Component
public class FileConverter implements FilesystemConverter<File, FilePO> {

    @Override
    public FilePO convert(File entity) {
        if (entity == null) {
            return null;
        }
        FilePO filePO = new FilePO();
        filePO.setAccessId(entity.getAccessId());
        filePO.setCreatedTime(entity.getCreatedTime());
        filePO.setDigest(entity.getDigest());
        filePO.setFileSystemId(entity.getFileSystemId());
        filePO.setId(entity.getId());
        filePO.setLocalPath(entity.getLocalPath());
        filePO.setModifiedTime(entity.getModifiedTime());
        filePO.setOriginalName(entity.getOriginalName());
        filePO.setSize(entity.getSize());
        filePO.setType(entity.getType());
        return filePO;
    }

    @Override
    public File convert(FilePO po) {
        if (po == null) {
            return null;
        }
        File file = new File();
        file.setAccessId(po.getAccessId());
        file.setCreatedTime(po.getCreatedTime());
        file.setDigest(po.getDigest());
        file.setFileSystemId(po.getFileSystemId());
        file.setId(po.getId());
        file.setLocalPath(po.getLocalPath());
        file.setModifiedTime(po.getModifiedTime());
        file.setOriginalName(po.getOriginalName());
        file.setSize(po.getSize());
        file.setType(po.getType());
        return file;
    }
    
}
