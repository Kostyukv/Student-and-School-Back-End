package com.example.demo.school;

import com.example.demo.student.Student;
import com.example.demo.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SchoolService {

    private final SchoolRepository schoolRepository;

    @Autowired
    public SchoolService(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public List<School> getSchools() {
        return schoolRepository.findAll();
    }

    public void addNewSchool(School school) {
        Optional<School> schoolOptional = schoolRepository
                .findSchoolByName(school.getName());
        if(schoolOptional.isPresent()){
            throw new IllegalStateException("email taken");
        }
        //Can do more validation here
        schoolRepository.save(school);
        //System.out.println(student);
    }

    public void deleteSchool(Long schoolId) {
        boolean exists = schoolRepository.existsById(schoolId);
        if(!exists){
            throw new IllegalStateException(
                    "student with id " + schoolId + " does not exist");
        }
        schoolRepository.deleteById(schoolId);
    }

    //update name and email
    @Transactional
    public void updateSchool(Long schoolId,
                             String name,
                             String city,
                             String state){
        School school = schoolRepository.findById(schoolId).
                orElseThrow(() -> new IllegalStateException(
                        "student with id " + schoolId + " does not exist")
                );


        if(name != null &&
                name.length() > 0 &&
                !Objects.equals(school.getName(), name)){
            Optional<School> schoolOptional = schoolRepository
                    .findSchoolByName(name);
            if(schoolOptional.isPresent()){
                throw new IllegalStateException("school exists");
            }
            school.setName(name);
        }

        if(city != null &&
                city.length() > 0 &&
                !Objects.equals(school.getCity(), city)){
            school.setCity(city);
        }

    }
}
