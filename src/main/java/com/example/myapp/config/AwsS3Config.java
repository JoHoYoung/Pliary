package com.example.myapp.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class AwsS3Config {

    @Autowired
    Environment env;

    @Bean
    public AmazonS3 amazonS3() {
        AmazonS3 amazonS3;
        AWSCredentials credentials = new BasicAWSCredentials(env.getProperty("aws.access"), env.getProperty("aws.secret"));
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTP);
        amazonS3 = new AmazonS3Client(credentials, clientConfig);
        amazonS3.setEndpoint("s3.ap-northeast-2.amazonaws.com"); // 엔드포인트 설정 [ 아시아 태평양 서울 ]
        return amazonS3;
    }
}
