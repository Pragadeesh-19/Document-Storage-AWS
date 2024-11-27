package org.pragadeesh.documentstorage.s3;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.pragadeesh.documentstorage.service.S3Service;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.io.InputStream;
import java.util.List;

public class S3ServiceTest {

    private final S3Client s3Client = Mockito.mock(S3Client.class);
    private final S3Service s3Service = new S3Service(s3Client);

    @Test
    void searchFiles_ShouldReturnMatchingFiles() {
        ListObjectsV2Response mockResponse = ListObjectsV2Response.builder()
                .contents(S3Object.builder().key("sandy/logistics-report-2024.pdf").build())
                .build();

        Mockito.when(s3Client.listObjectsV2(any(ListObjectsV2Request.class)))
                .thenReturn(mockResponse);
        
        List<String> result = s3Service.searchFiles("sandy", "logistics");

        assertEquals(1, result.size());
        assertEquals("sandy/logistics-report-2024.pdf", result.get(0));
    }

    @Test
    void downloadFile_ShouldReturnFileStream() {

        GetObjectResponse mockResponse = GetObjectResponse.builder().build();
        Mockito.when(s3Client.getObject(any(GetObjectRequest.class)))
                .thenReturn(new ResponseInputStream<>(mockResponse, Mockito.mock(InputStream.class)));

        java.io.InputStream filStream = s3Service.downloadFile("sandy", "logistics-report-2024.pdf");

        assert filStream != null;
    }
}
