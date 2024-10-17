package com.SpringBootFileSystem.service;

import com.SpringBootFileSystem.entity.Student;
import com.SpringBootFileSystem.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentService {

    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public Optional<Student> updateStudent(Long id, Student newStudent) {
        return studentRepository.findById(id).map(student -> {
            student.setName(newStudent.getName());
            student.setEmail(newStudent.getEmail());
            student.setCourse(newStudent.getCourse());
            student.setProfilePicture(newStudent.getProfilePicture());
            return studentRepository.save(student);
        });
    }

    public String storeFile(byte[] fileBytes, String originalFilename) throws IOException {
        String directory = UPLOAD_DIR;
        String filePath = directory + originalFilename;
        Files.createDirectories(Paths.get(directory));
        Files.write(Paths.get(filePath), fileBytes);
        return filePath;
    }

    public byte[] getFile(String fileName) throws IOException {
        Path filePath = Paths.get(UPLOAD_DIR + fileName);
        return Files.readAllBytes(filePath);
    }
}
