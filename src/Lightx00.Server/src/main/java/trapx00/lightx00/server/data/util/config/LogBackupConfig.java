package trapx00.lightx00.server.data.util.config;

import com.google.gson.annotations.Expose;

public class LogBackupConfig {
    @Expose
    private String endpoint;
    @Expose
    private String bucketName;
    @Expose
    private String accessKey;
    @Expose
    private String secretKey;

    public String getEndpoint() {
        return endpoint;
    }

    void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getBucketName() {
        return bucketName;
    }

    void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getAccessKey() {
        return accessKey;
    }

    void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
