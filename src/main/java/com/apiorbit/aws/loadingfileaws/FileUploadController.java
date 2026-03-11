package com.apiorbit.aws.loadingfileaws;

import com.apiorbit.aws.loadingfileaws.record.SignedUrlRequest;
import com.apiorbit.aws.loadingfileaws.service.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/upload")
public class FileUploadController {

    private final UploadService uploadService;

    @PostMapping("/presigned")
    public ResponseEntity<String> presignedUpload(@RequestBody SignedUrlRequest request) {
        return ResponseEntity.ok().body(uploadService.upload(request.fileName(), request.ContentType()).signedURL());

    }
}
