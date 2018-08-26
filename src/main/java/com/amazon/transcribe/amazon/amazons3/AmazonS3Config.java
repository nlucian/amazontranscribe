package com.amazon.transcribe.amazon.amazons3;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Lucian Nut
 */
@Configuration
public class AmazonS3Config {

    @Bean
    public AmazonS3 getAmazonS3Client() {
        return AmazonS3ClientBuilder.standard()
                .withRegion("us-east-2")
                .withCredentials(new ProfileCredentialsProvider())
                .build();
    }
}
