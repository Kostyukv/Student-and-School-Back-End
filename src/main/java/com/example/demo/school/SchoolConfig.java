package com.example.demo.school;

import com.example.demo.student.Student;
import com.example.demo.student.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

import static java.time.Month.JANUARY;

@Configuration
public class SchoolConfig {

    @Bean
    CommandLineRunner clrSchool(
            SchoolRepository repository) {
        return args -> {
            School MainlandHS = new School (
                    "Mainland High School",
                    "Daytona Beach",
                    "Florida"
            );

            School MatanzasHS = new School (
                    "Matanzas High School",
                    "Palm Coast",
                    "Florida"
            );

            repository.saveAll(
                    List.of(MainlandHS, MatanzasHS)
            );

        };
    }
}
