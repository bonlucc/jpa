package com.jpatest.springjpa.service;

import com.jpatest.springjpa.entity.Course;
import com.jpatest.springjpa.model.CourseModel;
import com.jpatest.springjpa.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService (CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

    public void create(CourseModel courseModel) {
        Course course = Course.builder()
                .title(courseModel.getTitle())
                .credit(courseModel.getCredit())
                .build();
        courseRepository.save(course);
    }

    public List<Course> takeCourses() {
        return courseRepository.findAll();
    }

}
