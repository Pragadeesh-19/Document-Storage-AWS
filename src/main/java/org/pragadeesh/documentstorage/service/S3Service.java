package org.pragadeesh.documentstorage.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public List<String> searchFiles(String username, String searchTerm) {

        try {

            String userFolder = username + "/";

            ListObjectsV2Request request = ListObjectsV2Request.builder()
                    .bucket(bucketName)
                    .prefix(userFolder)
                    .build();

            ListObjectsV2Response response = s3Client.listObjectsV2(request);

            return response.contents().stream()
                    .map(S3Object::key)
                    .filter(filename -> filename.contains(searchTerm))
                    .collect(Collectors.toList());
        } catch (S3Exception e) {
            throw new RuntimeException("AWS Error: " + e.awsErrorDetails().errorMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected Error: " + e.getMessage(), e);
        }

    }

    public InputStream downloadFile(String username, String fileName) {
        try {
            String objectKey = username + "/" + fileName;

            GetObjectRequest request = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();

            return s3Client.getObject(request);
        } catch (S3Exception e) {
            throw new RuntimeException("AWS S3 error: " + e.awsErrorDetails().errorMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected Error:" + e.getMessage(), e);
        }
    }

    public void uploadFile(String username, String fileName, byte[] fileContent) {
        try {
            String objectKey = username + "/" + fileName;

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();

            s3Client.putObject(request, RequestBody.fromBytes(fileContent));
        } catch (S3Exception e) {
            throw new RuntimeException("AWS S3 error: " + e.awsErrorDetails().errorMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected Error: " + e.getMessage(), e);
        }
    }
}
