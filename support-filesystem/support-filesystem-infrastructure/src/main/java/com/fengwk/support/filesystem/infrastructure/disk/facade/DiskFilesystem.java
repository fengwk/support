package com.fengwk.support.filesystem.infrastructure.disk.facade;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.LocalDate;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.fengwk.support.core.exception.ExceptionCodes;
import com.fengwk.support.filesystem.domain.facade.LocalFilesystem;
import com.fengwk.support.filesystem.domain.facade.LocalFilesystemRegistry;
import com.fengwk.support.filesystem.domain.model.LocalFile;
import com.fengwk.support.filesystem.domain.model.LocalFileInput;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author fengwk
 */
@Slf4j
@Validated
@Component
public class DiskFilesystem implements LocalFilesystem {

    static final String PATH_SEPARATOR = "/";
    
    @Value("${support.filesystem.disk.baseUri}")
    volatile URI baseUri;
    
    @Value("${support.filesystem.disk.id}")
    volatile String id;
    
    @Value("${support.filesystem.disk.root}")
    volatile String root;
    
    @Autowired
    volatile LocalFilesystemRegistry localFilesystemRegistry;
    
    @PostConstruct
    public void init() {
        localFilesystemRegistry.register(this);
    }
    
    @Override
    public String getId() {
        return id;
    }
    @Override
    public LocalFile store(LocalFileInput localFileInput) {
        String path = doStore(localFileInput);
        return new LocalFile(path);
    }

    private String doStore(LocalFileInput localFileInput) {
        String path = LocalDate.now() + PATH_SEPARATOR + localFileInput.getDigest();
        if (StringUtils.isNotBlank(localFileInput.getType())) {
            path += "." + localFileInput.getType();
        }
        
        File file;
        try {
            file = new File(new URL(root + path).getFile());
        } catch (MalformedURLException e) {
            log.error("文件存储异常.", e);
            throw ExceptionCodes.biz().create("文件存储异常");
        }
        if (file.exists()) {
            return path;
        }
        File dir = file.getParentFile();
        if (!dir.exists()) {
            System.out.println(dir.mkdirs());;
        }
        try (InputStream input = localFileInput.getInput(); FileOutputStream output = new FileOutputStream(file)) {
            IOUtils.copy(input, output);
        } catch (IOException e) {
            log.error("文件存储异常.", e);
            throw ExceptionCodes.biz().create("文件存储异常");
        }
        return path;
    }
    
    @Override
    public URI parse(LocalFile localFile) {
        return baseUri.resolve(localFile.getPath());
    }
    
}
