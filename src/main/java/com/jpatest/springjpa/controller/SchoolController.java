package com.jpatest.springjpa.controller;

import com.jpatest.springjpa.entity.Course;
import com.jpatest.springjpa.model.CourseModel;
import com.jpatest.springjpa.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/school")
public class SchoolController {

    private final CourseService courseService;

    @Autowired
    public SchoolController (CourseService courseService){
        this.courseService = courseService;
    }
    @PostMapping("/createCourse")
    public String createCourse(@RequestBody CourseModel courseModel){
        courseService.create(courseModel);
        return "Course sent";
    }

    @GetMapping("/getCourses")
    public List<Course> getCourses(){
        return courseService.takeCourses();
    }
}
