package com.apiorbit.aws.loadingfileaws.service;

import software.amazon.awssdk.services.sqs.model.Message;

public interface NotificationService {

    void readNotifications();
}
