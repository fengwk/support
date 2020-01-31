package com.fengwk.support.filesystem.web.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fengwk.support.core.result.Response;
import com.fengwk.support.core.result.Responses;
import com.fengwk.support.filesystem.api.model.FileInputDTO;
import com.fengwk.support.filesystem.api.service.FileApiService;

/**
 * 
 * @author fengwk
 */
@Validated
@CrossOrigin
@RequestMapping("/file")
@RestController
public class FileController {

    @Autowired
    volatile FileApiService fileApiService;
    
    @PostMapping("/upload")
    public Response<String> upload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        return Responses.success(fileApiService.upload(new FileInputDTO(multipartFile.getInputStream(), multipartFile.getOriginalFilename())));
    }
    
    @PostMapping("/uri")
    public Response<String> getUri(@RequestParam("accessId") String accessId) throws IOException {
        return Responses.success(fileApiService.getUri(accessId));
    }
    
}
