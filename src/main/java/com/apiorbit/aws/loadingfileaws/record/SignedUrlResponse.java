package com.apiorbit.aws.loadingfileaws.record;

public record SignedUrlResponse(
        String bucket,
        String signedURL
) {
}
