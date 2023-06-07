package com.jpatest.springjpa.repository;

import com.jpatest.springjpa.entity.Course;
import com.jpatest.springjpa.entity.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TeacherRepositoryTest {

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    public void saveTeacher() {
        Course course1 = Course.builder()
                .title("ED")
                .credit(20)
                .build();
        Course course2 = Course.builder()
                .title("IT")
                .credit(10)
                .build();
        Teacher teacher = Teacher.builder()
                .firstName("Albert")
                .lastName("Einstein")
                //.courses(List.of(course1, course2))
                .build();
        teacherRepository.save(teacher);
    }
}
