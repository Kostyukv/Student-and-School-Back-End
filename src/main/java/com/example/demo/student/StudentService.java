package com.example.demo.student;

import com.example.demo.school.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.example.demo.school.SchoolRepository;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final SchoolRepository schoolRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, SchoolRepository schoolRepository) {
        this.studentRepository = studentRepository;
        this.schoolRepository = schoolRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public List<Student> getStudentsInSchool(Long schoolId){
        Optional<School> schoolOptional = schoolRepository
                .findById(schoolId);
        if(schoolOptional.isEmpty()) {
            throw new IllegalStateException("school doesn't exist");
        }
        return studentRepository.findStudentsBySchoolId(schoolId);
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository
                .findStudentByEmail(student.getEmail());
        if(studentOptional.isPresent()){
            throw new IllegalStateException("email taken");
        }
        //Can do more validation here
        studentRepository.save(student);
        System.out.println(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if(!exists){
            throw new IllegalStateException(
                    "student with id " + studentId + " does not exist");
        }
        studentRepository.deleteById(studentId);
    }



    //update name and email
    @Transactional
    public void updateStudent(Long studentId,
                              String email,
                              String name){
        Student student = studentRepository.findById(studentId).
                orElseThrow(() -> new IllegalStateException(
                        "student with id " + studentId + " does not exist")
                );


        if(email != null &&
                email.length() > 0 &&
                !Objects.equals(student.getEmail(), email)){
            Optional<Student> studentOptional = studentRepository
                    .findStudentByEmail(email);
            if(studentOptional.isPresent()){
                throw new IllegalStateException("email exists");
            }
            student.setEmail(email);
        }

        if(name != null &&
                name.length() > 0 &&
                !Objects.equals(student.getName(), name)){
            student.setName(name);
        }

    }
}
