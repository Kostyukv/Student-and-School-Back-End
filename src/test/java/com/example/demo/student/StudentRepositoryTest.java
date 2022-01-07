package com.example.demo.student;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository underTest;

    @Test
    void itShouldFindStudentByEmail() {
        List<Student> studentList = new ArrayList<>();
        Optional<Student> expectedStudent;
        //given
        Student student1 = new Student(
                "Noelle1",
                "Noelle1@gmail.com",
                LocalDate.of(1999,3,1),
                1
        );
        Student student2 = new Student(
                "Noelle2",
                "Noelle2@gmail.com",
                LocalDate.of(1999,3,1),
                1
        );
        Student student3 = new Student(
                "Noelle3",
                "Noelle3@gmail.com",
                LocalDate.of(1999,3,1),
                2
        );
        Student student4 = new Student(
                "Noelle4",
                "Noelle4@gmail.com",
                LocalDate.of(1999,3,1),
                2
        );
        studentList.add(student1);
        studentList.add(student2);
        studentList.add(student3);
        studentList.add(student4);
        underTest.saveAll(studentList);

        //when
        expectedStudent = Optional.of(student3);
        String emailToTest = "Noelle3@gmail.com";
        Optional<Student> foundStudent = underTest.findStudentByEmail(emailToTest);
        //then
        assertThat(foundStudent).isEqualTo(expectedStudent);
    }

    @Test
    void itShouldFindStudentsBySchoolId() {
        List<Student> studentList = new ArrayList<>();
        List<Student> expectedStudents = new ArrayList<>();

        //given
        Student student1 = new Student(
                "Noelle1",
                "Noelle1@gmail.com",
                LocalDate.of(1999,3,1),
                1
        );
        Student student2 = new Student(
                "Noelle2",
                "Noelle2@gmail.com",
                LocalDate.of(1999,3,1),
                1
        );
        Student student3 = new Student(
                "Noelle3",
                "Noelle3@gmail.com",
                LocalDate.of(1999,3,1),
                2
        );
        Student student4 = new Student(
                "Noelle4",
                "Noelle4@gmail.com",
                LocalDate.of(1999,3,1),
                2
        );
        studentList.add(student1);
        studentList.add(student2);
        studentList.add(student3);
        studentList.add(student4);
        underTest.saveAll(studentList);

        expectedStudents.add(student3);
        expectedStudents.add(student4);

        //when
        Long schoolIdToTest = 2L;
        List<Student> studentList2 = new ArrayList<>(underTest.findStudentsBySchoolId(schoolIdToTest));
        //then
        assertThat(studentList2).isEqualTo(expectedStudents);
    }
}