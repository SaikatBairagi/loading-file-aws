package com.apiorbit.aws.loadingfileaws.record;

public record ReadRequest(
        String bucket,
        String key
) {
}
