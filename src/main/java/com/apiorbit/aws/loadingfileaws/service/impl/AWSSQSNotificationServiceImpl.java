package com.apiorbit.aws.loadingfileaws.service.impl;

import com.apiorbit.aws.loadingfileaws.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AWSSQSNotificationServiceImpl implements NotificationService {

    private final SqsClient sqsClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${aws.sqs.queueURL}")
    private String queueUrl;

    @Scheduled(fixedDelay = 5000)
    @Override
    public void readNotifications() {
        ReceiveMessageRequest request = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(10)
                .waitTimeSeconds(10)
                .build();

        ReceiveMessageResponse receiveMessageResponse = sqsClient.receiveMessage(request);
        List<Message> messageList = receiveMessageResponse.messages();
        log.info("Received {} messages", messageList.size());
        if(messageList.isEmpty()){
            log.info("No messages found");
            return;
        }

        for(Message message : messageList){
            processMessage(message);

            DeleteMessageRequest deleteRequest = DeleteMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .receiptHandle(message.receiptHandle())
                    .build();

            sqsClient.deleteMessage(deleteRequest);

            System.out.println("Message deleted");
        }



    }

    private void processMessage(Message message) {
        JsonNode root = objectMapper.readTree(message.body());
        JsonNode records = root.path("Records");
        if (!records.isArray() || records.isEmpty()) {
            log.warn("SQS message does not contain S3 Records: {}", message.body());
            return;
        }
        for (JsonNode record : records) {
            String bucket = record.path("s3").path("bucket").path("name").asText();
            String encodedKey = record.path("s3").path("object").path("key").asText();
            String key = URLDecoder.decode(encodedKey, StandardCharsets.UTF_8);

            log.info("Received S3 upload event. bucket={}, key={}", bucket, key);
        }

    }
}
