package com.example.demo.school;

import com.example.demo.student.Student;
import com.example.demo.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/school")
public class SchoolController {

    private final SchoolService schoolService;
    private final StudentService studentService;

    @Autowired
    public SchoolController(SchoolService schoolService, StudentService studentService){
        this.schoolService = schoolService;
        this.studentService = studentService;
    }

    @GetMapping(path = "/students")
    public List<Student> getStudentsInSchool(@RequestParam Long schoolId) {
        return studentService.getStudentsInSchool(schoolId);
    }

    @GetMapping
    public List<School> getSchools() {
        return schoolService.getSchools();
    }

    @PostMapping
    public void addNewSchool(@RequestBody School school){
        schoolService.addNewSchool(school);
    }

    @DeleteMapping(path = "{schoolId}")
    public void deleteSchool(
            @PathVariable("schoolId") Long schoolId){
        schoolService.deleteSchool(schoolId);
    }

    @PutMapping(path = "{schoolId}")
    public void updateSchool(
            @PathVariable("schoolId") Long schoolId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state){
        schoolService.updateSchool(schoolId, name, city, state);
    }

}
