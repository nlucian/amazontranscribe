package com.amazon.transcribe;

import com.amazon.transcribe.constants.TranscriberConstants;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.transcribe.AmazonTranscribeAsync;
import com.amazonaws.services.transcribe.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;


/**
 * @author Lucian Nut
 */
@Component
public class AmazonTranscribeOperation {

    @Autowired
    private AmazonTranscribeAsync amazonTranscribe;

    @Autowired
    private AmazonS3 amazonS3;


    /**
     * Call this method in order to upload a file to the Amazon S3 bucket.
     *
     * @param bucketName
     * @param fileName
     * @param fullFileName
     */
    public void uploadFileToBucket(String bucketName, String fileName, String fullFileName) {
        PutObjectRequest request = new PutObjectRequest(bucketName, fileName, new File(fullFileName));
        amazonS3.putObject(request);
    }

    /**
     * Call this method with the appropriate parameters in order to transcribe the file with <code>fileName</code>
     * present in the <code>bucketName</code>.
     *
     * @param bucketName
     * @param fileName
     */
    protected void transcribe(String bucketName, String fileName, String jobName) {
        StartTranscriptionJobRequest startTranscriptionJobRequest = new StartTranscriptionJobRequest();
        Media media = new Media();
        media.setMediaFileUri(amazonS3.getUrl(bucketName, fileName).toString());
        startTranscriptionJobRequest.withMedia(media)
                .withLanguageCode(LanguageCode.EnUS)
                .withMediaFormat(TranscriberConstants.MediaFilesTypes.WAV.type())
                .withOutputBucketName(TranscriberConstants.BUCKET_NAME)
                .setTranscriptionJobName(jobName);
        amazonTranscribe.startTranscriptionJob(startTranscriptionJobRequest);
    }

    /**
     * Call this method to get the result of the process.
     * This method could be parameterized / it could look in a queue for
     * the required to be translated files etc..
     *
     *
     * @return String containing the result of the transcribe process
     */
    public String getTranscriptResult(String bucketName, String key) {
        TranscriptionJob transcriptionJob = retrieveObjectWhenJobCompleted();
        if (transcriptionJob != null && !TranscriptionJobStatus.FAILED.equals(transcriptionJob.getTranscriptionJobStatus())) {
            return amazonS3.getObjectAsString(bucketName, key);
        } else
            return "";
    }

    private TranscriptionJob retrieveObjectWhenJobCompleted() {
        GetTranscriptionJobRequest getTranscriptionJobRequest = new GetTranscriptionJobRequest();
        getTranscriptionJobRequest.setTranscriptionJobName(TranscriberConstants.TRANSCRIPTION_JOB_NAME);

        while (true) {
            GetTranscriptionJobResult innerResult = amazonTranscribe.getTranscriptionJob(getTranscriptionJobRequest);
            String status = innerResult.getTranscriptionJob().getTranscriptionJobStatus();
            if (TranscriptionJobStatus.COMPLETED.name().equals(status) ||
                    TranscriptionJobStatus.FAILED.name().equals(status)) {
                return innerResult.getTranscriptionJob();
            }
        }
    }
}
