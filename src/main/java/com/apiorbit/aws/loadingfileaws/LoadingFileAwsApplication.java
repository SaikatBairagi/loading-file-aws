package com.apiorbit.aws.loadingfileaws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class LoadingFileAwsApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoadingFileAwsApplication.class, args);
    }

}
