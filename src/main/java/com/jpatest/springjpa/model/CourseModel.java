package com.jpatest.springjpa.model;


import com.jpatest.springjpa.entity.CourseMaterial;
import com.jpatest.springjpa.entity.Student;
import com.jpatest.springjpa.entity.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseModel {

    //private Long courseId;
    private String title;
    private Integer credit;
    //private CourseMaterial courseMaterial;
    //private Teacher teacher;
    //private List<Student> students;

}
