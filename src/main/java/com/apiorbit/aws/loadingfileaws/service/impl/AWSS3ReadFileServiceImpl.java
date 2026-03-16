package com.apiorbit.aws.loadingfileaws.service.impl;

import com.apiorbit.aws.loadingfileaws.service.ReadService;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class AWSS3ReadFileServiceImpl implements ReadService {

    private final S3Client s3Client;

    @Override
    public String readFile(
            String bucket,
            String key) {

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();
        String text = null;
        try{
            ResponseInputStream<GetObjectResponse> object = s3Client.getObject(getObjectRequest);
            PDDocument document = PDDocument.load(object);
            PDFTextStripper textStripper = new PDFTextStripper();

            text = textStripper.getText(document);
        }catch(IOException exception){
            exception.printStackTrace();
        }
        return text;
    }
}
