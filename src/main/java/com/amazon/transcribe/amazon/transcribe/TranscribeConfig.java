package com.amazon.transcribe.amazon.transcribe;

import com.amazonaws.services.transcribe.AmazonTranscribeAsync;
import com.amazonaws.services.transcribe.AmazonTranscribeAsyncClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Lucian Nut
 */
@Configuration
public class TranscribeConfig {

    public static final String REGION = "us-east-2";

    @Bean
    public AmazonTranscribeAsync getAmazonTranscribeClient() {
        return AmazonTranscribeAsyncClient
                .asyncBuilder()
                .withRegion(REGION)
                .build();
    }
}
