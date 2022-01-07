package com.example.demo.student;

import com.example.demo.school.School;
import com.example.demo.school.SchoolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.time.Month.JANUARY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    @Mock
    private SchoolRepository schoolRepository;
    private AutoCloseable autoCloseable;
    @Mock
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        studentService = new StudentService(studentRepository,schoolRepository);
    }

    @Test
    void canGetStudents() {
        //when
        studentService.getStudents();
        //then
        verify(studentRepository).findAll();
    }

    @Test
    void canGetStudentsInSchool() {
        Long testSchoolId = 1L;
        Student testStudent = new Student(
                1L,
                "Noelle",
                "NK@Gmail.com",
                LocalDate.of(1999, 3, 1),
                testSchoolId);
        School testSchool = new School(
                testSchoolId,
                "OKES",
                "Flagler Beach",
                "FL");
        when(schoolRepository.findById(testSchoolId)).
                thenReturn(Optional.of(testSchool));
        when(studentRepository.findStudentsBySchoolId(testSchoolId)).
                thenReturn(List.of(testStudent));
        studentService.getStudentsInSchool(testSchoolId);
        verify(studentRepository).findStudentsBySchoolId(testSchoolId);
    }

    @Test
    void canAddNewStudent() {
        Student testStudent = new Student("Noelle",
                "noellekostyuk@gmail.com",
                LocalDate.of(2000, JANUARY, 5),
                2);
        //when
        studentService.addNewStudent(testStudent);
        //then
        ArgumentCaptor<Student> studentArgumentCaptor =
                ArgumentCaptor.forClass(Student.class);
        verify(studentRepository)
                .save(studentArgumentCaptor.capture());

        Student capturedStudent = studentArgumentCaptor.getValue();

        assertThat(capturedStudent).isEqualTo(testStudent);
    }

    @Test
    void canDeleteStudent() {
        Long testStudentID = 1L;
        when(studentRepository.existsById(testStudentID)).thenReturn(true);
        studentService.deleteStudent(testStudentID);
        //then
        ArgumentCaptor<Student> studentArgumentCaptor =
                ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).deleteById(testStudentID);
    }

    @Test
    void canUpdateStudentName() {
        Long testStudentID = 1L;
        Student testStudent = spy(new Student(
                testStudentID,
                "Noelle",
                "noellekostyuk@gmail.com",
                LocalDate.of(2000, JANUARY, 5),
                2));
        when(studentRepository.findById(testStudentID)).
                thenReturn(Optional.of(testStudent));

        studentService.updateStudent(
                testStudentID,
                null,
                "Noelle2"
        );
        verify(testStudent).setName("Noelle2");
    }

    @Test
    void canUpdateStudentEmail() {
        Long testStudentID = 1L;
        Student testStudent = spy(new Student(
                testStudentID,
                "Noelle",
                "noellekostyuk@gmail.com",
                LocalDate.of(2000, JANUARY, 5),
                2));
        when(studentRepository.findById(testStudentID)).
                thenReturn(Optional.of(testStudent));

        studentService.updateStudent(
                testStudentID,
                "NewGmail@Yahoo.com",
                null
        );
        verify(testStudent).setEmail("NewGmail@Yahoo.com");
    }
}