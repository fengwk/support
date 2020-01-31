package com.fengwk.support.filesystem.infrastructure.mysql.model;

import javax.persistence.Table;

import com.fengwk.support.filesystem.infrastructure.mysql.FilesystemPO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author fengwk
 */
@Table(name = "File")
@EqualsAndHashCode(callSuper = false)
@Data
public class FilePO extends FilesystemPO {
    
    String accessId;
    String fileSystemId;
    String localPath;
    String originalName;
    String type;
    String digest;
    Long size;
    
}
