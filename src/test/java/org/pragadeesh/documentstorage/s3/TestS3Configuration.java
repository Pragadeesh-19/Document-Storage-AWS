package org.pragadeesh.documentstorage.s3;

import lombok.RequiredArgsConstructor;
import org.pragadeesh.documentstorage.service.S3Service;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestS3Configuration implements CommandLineRunner {

    private final S3Service s3Service;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("testing s3 integration...");

        String username = "sandy";
        String searchTerm = "logistics";
        var files = s3Service.searchFiles(username, searchTerm);

        System.out.println("Found files: " + files);
    }
}
