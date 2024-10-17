package com.SpringBootCRUD.service;
import com.SpringBootCRUD.entity.Student;
import com.SpringBootCRUD.exception.StudentNotFoundException;
import com.SpringBootCRUD.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    private Student student;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        student = new Student();
        student.setId(1L);
        student.setName("prathamesh");
        student.setEmail("prathamesh@gmail.com");
        student.setAge(20);
        student.setCourse("Blockchain");
        student.setEnrollmentDate(LocalDate.of(2022, 1, 1));
    }

    @Test
    void getAllStudentsTest() {
        when(studentRepository.findAll()).thenReturn(Arrays.asList(student));
        assertFalse(studentService.getAllStudents().isEmpty());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void getStudentByIdTest_Success() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        Student foundStudent = studentService.getStudentById(1L);
        assertNotNull(foundStudent);
        assertEquals("prathamesh", foundStudent.getName());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void getStudentByIdTest_NotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(StudentNotFoundException.class, () -> studentService.getStudentById(1L));
    }

    @Test
    void createStudentTest() {
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        Student createdStudent = studentService.createStudent(student);
        assertNotNull(createdStudent);
        assertEquals("prathamesh", createdStudent.getName());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void updateStudentTest() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student updatedStudent = studentService.updateStudent(1L, student);
        assertNotNull(updatedStudent);
        assertEquals("prathamesh", updatedStudent.getName());
        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void deleteStudentTest() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        doNothing().when(studentRepository).delete(student);

        studentService.deleteStudent(1L);
        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).delete(student);
    }
}
