package com.amazon.transcribe.constants;

/**
 * @author Lucian Nut
 */
public class TranscriberConstants {

    public static final String TRANSCRIPTION_JOB_NAME = "job_name02";
    public static final String TRANSCRIPTION_RESULT_FILE_EXTENSION = ".json";
    public static final String BUCKET_NAME = "lnvbucket01";
    public static final String FILE_NAME = "ENG_M.wav";

    private TranscriberConstants() {
    }

    public enum MediaFilesTypes {

        WAV("wav"), FLAT("flat");

        private String type;

        MediaFilesTypes(String type) {
            this.type = type;
        }

        public String type() {
            return type;
        }
    }
}
