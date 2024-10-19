package com.SpringBootFileSystem.service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StudentService {
    private final String storageDirectory = "file_storage";

    public StudentService() {
        // Create storage directory if it doesn't exist
        File directory = new File(storageDirectory);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    public String saveFile(MultipartFile file) throws IOException {
        Path filePath = Paths.get(storageDirectory, file.getOriginalFilename());
        Files.write(filePath, file.getBytes());
        return "File uploaded successfully: " + file.getOriginalFilename();
    }

    public byte[] readFile(String fileName) throws IOException {
        Path filePath = Paths.get(storageDirectory, fileName);
        return Files.readAllBytes(filePath);
    }

    public String updateFile(String fileName, MultipartFile newFile) throws IOException {
        Path filePath = Paths.get(storageDirectory, fileName);
        if (Files.exists(filePath)) {
            Files.write(filePath, newFile.getBytes());
            return "File updated successfully: " + fileName;
        } else {
            return "File not found: " + fileName;
        }
    }

    public String deleteFile(String fileName) throws IOException {
        Path filePath = Paths.get(storageDirectory, fileName);
        if (Files.exists(filePath)) {
            Files.delete(filePath);
            return "File deleted successfully: " + fileName;
        } else {
            return "File not found: " + fileName;
        }
    }
}

