package com.apiorbit.aws.loadingfileaws.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
@Slf4j
public class configAwsBucket {

    @Autowired
    private ApplicationContext context;

//    @Value("${aws.accessKey}")
//    private String accessKey;
//
//    @Value("${aws.secretAccessKey}")
//    private String secretKey;

    @Value("${aws.region}")
    private String region;


    @Bean
    public S3Client getS3Client() {

//        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        return S3Client.builder()
                .region(Region.of(region))
//                .credentialsProvider(
//                        StaticCredentialsProvider.create(credentials)
//                )
                .build();
    }

    @Bean
    public S3Presigner s3Presigner() {
//        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        return S3Presigner.builder()
                .region(Region.of(region))
//                .credentialsProvider(
//                        StaticCredentialsProvider.create(credentials)
//                )
                .build();
    }

    @Bean
    public SqsClient getSqsClient() {
//        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        return SqsClient.builder()
                .region(Region.of(region))
//                .credentialsProvider(
//                        StaticCredentialsProvider.create(credentials)
//                )
                .build();
    }


    @PostConstruct
    public void checkBean() {

        boolean exists = context.containsBean("getS3Client");

        System.out.println("S3Client bean exists: " + exists);
    }
}
