package com.SpringBootFileSystem.controller;
import com.SpringBootFileSystem.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/v1/files")
public class StudentController{

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String response = fileStorageService.saveFile(file);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed");
        }
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName) {
        try {
            byte[] fileData = fileStorageService.readFile(fileName);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", fileName);
            return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PutMapping("/update/{fileName}")
    public ResponseEntity<String> updateFile(@PathVariable String fileName, @RequestParam("file") MultipartFile newFile) {
        try {
            String response = fileStorageService.updateFile(fileName, newFile);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File update failed");
        }
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        try {
            String response = fileStorageService.deleteFile(fileName);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
        }
    }
}
