package com.fengwk.support.filesystem.application.service;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fengwk.support.filesystem.api.model.FileInputDTO;
import com.fengwk.support.filesystem.api.service.FileApiService;
import com.fengwk.support.filesystem.domain.model.File;
import com.fengwk.support.filesystem.domain.model.FileInput;
import com.fengwk.support.filesystem.domain.service.FileService;

/**
 * 
 * @author fengwk
 */
@Validated
@Transactional
@Service
public class FileApiServiceImpl implements FileApiService {

    @Autowired
    volatile FileService FileService;
    
    @Override
    public String upload(FileInputDTO fileInputDTO) {
        File file = FileService.upload(new FileInput(fileInputDTO.getInput(), fileInputDTO.getOriginalName()));
        return file.getAccessId();
    }

    @Override
    public String getUri(String accessId) {
        URI uri = FileService.getUri(accessId);
        if (uri == null) {
            return null;
        }
        return uri.toASCIIString();
    }

}
