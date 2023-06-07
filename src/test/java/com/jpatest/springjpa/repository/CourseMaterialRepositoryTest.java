package com.jpatest.springjpa.repository;

import com.jpatest.springjpa.entity.Course;
import com.jpatest.springjpa.entity.CourseMaterial;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseMaterialRepositoryTest {
    @Autowired
    private CourseMaterialRepository courseMaterialRepository;

    //@Autowired
    //private CourseRepository courseRepository;

    @Test
    public void saveCourseMaterial(){
        Course course = Course.builder()
                .title("ED")
                .credit(20)
                .build();
        CourseMaterial courseMaterial = CourseMaterial.builder()
                .url("www.google.com")
                .course(course)
                .build();

        //courseRepository.save(course);
        courseMaterialRepository.save(courseMaterial);
    }
    @Test
    public void printAllCourseMaterials(){
        List<CourseMaterial> courseMaterialList = courseMaterialRepository.findAll();
        System.out.println("courseMaterials = " + courseMaterialList);

    }
}