package com.apiorbit.aws.loadingfileaws.service;

import com.apiorbit.aws.loadingfileaws.record.SignedUrlResponse;

public interface UploadService {

    SignedUrlResponse upload(String fileName, String contentType);
}
