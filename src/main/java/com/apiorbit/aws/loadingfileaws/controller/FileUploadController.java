package com.apiorbit.aws.loadingfileaws.controller;

import com.apiorbit.aws.loadingfileaws.record.ReadRequest;
import com.apiorbit.aws.loadingfileaws.record.SignedUrlRequest;
import com.apiorbit.aws.loadingfileaws.service.ReadService;
import com.apiorbit.aws.loadingfileaws.service.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/upload")
public class FileUploadController {

    private final UploadService uploadService;
    private final ReadService readService;

    @PostMapping("/presigned")
    public ResponseEntity<String> presignedUpload(@RequestBody SignedUrlRequest request) {
        Long userId = 1L;
        return ResponseEntity.ok().body(uploadService.upload(userId, request.fileName(), request.ContentType()).signedURL());

    }


    @GetMapping("/readfile")
    public ResponseEntity<String> getCVContent(@RequestBody ReadRequest readRequest){
        return ResponseEntity.ok().body(readService.readFile(readRequest.bucket(), readRequest.key()));

    }
}
