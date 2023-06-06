package com.jpatest.springjpa.repository;

import com.jpatest.springjpa.entity.Guardian;
import com.jpatest.springjpa.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Guard;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;
    //@BeforeEach
    //void setUp() {
    //}
    @Test
    public void saveStudentWithGuardian() {
        Guardian guardian = Guardian.builder()
                .email("nikhil@email.com")
                .name("Nikhil")
                .mobile("999999999")
                .build();
        Student student = Student.builder()
                .firstName("Shivam")
                .email("shivam@email.com")
                .lastName("Kinar")
                .guardian(guardian)
                .build();
        studentRepository.save(student);
    }

}