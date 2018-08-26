package com.amazon.transcribe;

import com.amazon.transcribe.constants.TranscriberConstants;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * @author Lucian Nut
 */
public class ApplicationStarter {


    private static final String APPLICATION_CONTEXT_XML_FILE = "applicationContext.xml";
    private static final String AMAZON_OPERATIONS = "amazonTranscribeOperation";

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT_XML_FILE);
        AmazonTranscribeOperation operations = (AmazonTranscribeOperation) ctx.getBean(AMAZON_OPERATIONS);

        //prior to transcribing this audio file it must be uploaded either manually
        //or by calling AmazonTranscribeOperation#uploadFileToBucket
        operations.transcribe(TranscriberConstants.BUCKET_NAME, TranscriberConstants.FILE_NAME, TranscriberConstants.TRANSCRIPTION_JOB_NAME);

        //String result = operations.getTranscriptResult(TranscriberConstants.BUCKET_NAME,
        //        TranscriberConstants.TRANSCRIPTION_JOB_NAME + TranscriberConstants.TRANSCRIPTION_RESULT_FILE_EXTENSION);
    }
}
