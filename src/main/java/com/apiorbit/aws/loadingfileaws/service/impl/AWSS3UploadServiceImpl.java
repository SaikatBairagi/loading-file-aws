package com.apiorbit.aws.loadingfileaws.service.impl;

import com.apiorbit.aws.loadingfileaws.record.SignedUrlResponse;
import com.apiorbit.aws.loadingfileaws.service.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AWSS3UploadServiceImpl implements UploadService {

    private final S3Presigner  s3Presigner;

    @Value("${aws.s3.bucketname}")
    private String bucket;

    @Override
    public SignedUrlResponse upload(
            String fileName,
            String contentType) {
        String safeName = fileName == null ? "file" : fileName.replaceAll("[^a-zA-Z0-9._-]", "_");
        String key = "videos/" + UUID.randomUUID() + "-" + safeName;


        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(contentType)
                .build();

        PutObjectPresignRequest putObjectPresignRequest = PutObjectPresignRequest.builder()
                .putObjectRequest(putObjectRequest)
                .signatureDuration(Duration.ofMinutes(10))
                .build();

        PresignedPutObjectRequest presigned = s3Presigner.presignPutObject(putObjectPresignRequest);

        SignedUrlResponse signedUrlResponse = new SignedUrlResponse(bucket,presigned.url().toString());

        return signedUrlResponse;
    }
}
