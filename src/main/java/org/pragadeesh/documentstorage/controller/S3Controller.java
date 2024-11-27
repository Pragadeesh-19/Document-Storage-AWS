package org.pragadeesh.documentstorage.controller;

import lombok.RequiredArgsConstructor;
import org.pragadeesh.documentstorage.service.S3Service;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1/storage")
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    @GetMapping("/search")
    public ResponseEntity<List<String>> searchFile(@RequestParam String username,
                                                   @RequestParam String searchTerm) {

        List<String> files = s3Service.searchFiles(username, searchTerm);
        return ResponseEntity.ok(files);
    }

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadFile(@RequestParam String username,
                                                            @RequestParam String fileName) {
        InputStream fileStream = s3Service.downloadFile(username, fileName);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
                .header(HttpHeaders.PRAGMA, "no-cache")
                .header(HttpHeaders.EXPIRES, "0")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(fileStream));
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam String username,
                                             @RequestParam MultipartFile file) {
        try {
            s3Service.uploadFile(username, file.getOriginalFilename(), file.getBytes());
            return ResponseEntity.ok("File Uploaded Successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
        }
    }
}
