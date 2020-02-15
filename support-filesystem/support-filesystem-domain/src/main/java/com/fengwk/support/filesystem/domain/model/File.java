package com.fengwk.support.filesystem.domain.model;

import com.fengwk.support.core.convention.exception.Preconditions;
import com.fengwk.support.core.util.UuidUtils;
import com.fengwk.support.filesystem.domain.FilesystemEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author fengwk
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class File extends FilesystemEntity {

    String accessId;
    String fileSystemId;
    String localPath;
    String originalName;
    String type;
    String digest;
    long size;// bytes

    public static File of(String fileSystemId, String localPath, String originalName, String type, String digest, long size) {
        Preconditions.notBlank(fileSystemId, "文件系统标识不能为空");
        Preconditions.notBlank(localPath, "本地路径不能为空");
        Preconditions.notBlank(digest, "文件摘要不能为空");
        File file = new File();
        file.accessId = UuidUtils.genShort();
        file.fileSystemId = fileSystemId;
        file.localPath = localPath;
        file.originalName = originalName;
        file.type = type;
        file.digest = digest;
        file.size = size;
        return file;
    }
    
}
