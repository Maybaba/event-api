package com.study.event.api.event.controller;

import com.study.event.api.event.dto.request.EventUserSaveDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
public class FileUploadController {

    // 파일 업로드 처리
    @PostMapping("/file/upload")
    public ResponseEntity<?> upload(
            @RequestPart(value = "userData") EventUserSaveDto dto,
            @RequestPart(value = "profileImage") MultipartFile uploadFile
    ) {

        log.info("userData: {}", dto);
        log.info("profileImage: {}", uploadFile.getOriginalFilename());

        return ResponseEntity.ok().body("OK");
    }

    //파일을 업로드




}

