package com.fengwk.support.filesystem.domain.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.fengwk.support.core.domain.exception.DomainException;
import com.fengwk.support.core.util.FileTypeParser;
import com.fengwk.support.filesystem.domain.facade.LocalFilesystem;
import com.fengwk.support.filesystem.domain.facade.LocalFilesystemRegistry;
import com.fengwk.support.filesystem.domain.model.File;
import com.fengwk.support.filesystem.domain.model.FileInput;
import com.fengwk.support.filesystem.domain.model.LocalFile;
import com.fengwk.support.filesystem.domain.model.LocalFileInput;
import com.fengwk.support.filesystem.domain.repo.FileRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author fengwk
 */
@Slf4j
@Validated
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    volatile LocalFilesystemRegistry localFilesystemRegistry;
    
    @Autowired
    volatile FileRepository fileRepository;
    
    @Override
    public File upload(FileInput fileInput) {
        if (fileInput == null) {
            return null;
        }
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            IOUtils.copy(fileInput.getInput(), output);
            byte[] data = output.toByteArray();
            String digest = DigestUtils.md5Hex(data);
            
            File file = fileRepository.getByDigest(digest);
            if (file != null) {
                return file;
            }
            
            file = store(new LocalFileInput(new ByteArrayInputStream(data), fileInput.getOriginalName(), FileTypeParser.parse(data), digest, data.length));
            fileRepository.add(file);
            return file;
        } catch (IOException e) {
            log.error("存储文件异常.", e);
            throw new DomainException("存储文件异常");
        } finally {
            try {
                fileInput.getInput().close();
            } catch (IOException e) {
                log.error("关闭输入流异常.", e);
                throw new DomainException("关闭输入流异常");
            }
        }
    }
    
    private File store(LocalFileInput localFileInput) {
        LocalFilesystem localFilesystem = localFilesystemRegistry.selectOne();
        LocalFile localFile = localFilesystem.store(localFileInput);
        File file = File.of(localFilesystem.getId(), localFile.getPath(), localFileInput.getOriginalName(), localFileInput.getType(), localFileInput.getDigest(), localFileInput.getSize());
        return file;
    }

    @Override
    public URI getUri(String accessId) {
        File file = fileRepository.getByAccessId(accessId);
        if (file == null) {
            return null;
        }
        LocalFilesystem filesystem = localFilesystemRegistry.get(file.getFileSystemId());
        return filesystem.parse(new LocalFile(file.getLocalPath()));
    }

}
