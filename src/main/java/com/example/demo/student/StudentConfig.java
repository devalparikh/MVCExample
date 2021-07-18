package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

import static java.time.Month.*;

@Configuration
public class StudentConfig {

    // Access to repository
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student billy = new Student(
                    1L,
                    "Billy",
                    "Billy@gmail.com",
                    LocalDate.of(2000, APRIL, 5)
            );
            Student bob = new Student(
                    "Bob",
                    "Bob@gmail.com",
                    LocalDate.of(2004, APRIL, 6)
            );

            repository.saveAll(List.of(billy, bob));

        };
    }
}
