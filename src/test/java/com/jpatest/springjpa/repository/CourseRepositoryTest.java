package com.jpatest.springjpa.repository;

import com.jpatest.springjpa.entity.Course;
import com.jpatest.springjpa.entity.Student;
import com.jpatest.springjpa.entity.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@SpringBootTest
class CourseRepositoryTest {
    @Autowired
    private CourseRepository courseRepository;

    @Test
    public void printCourses(){
        List<Course> courses =
                courseRepository.findAll();

        System.out.println("courses = " + courses);
    }
    @Test
    public void saveCourseWithTeacher(){
        Teacher teacher = Teacher.builder()
                .firstName("Priyanka")
                .lastName("Singh")
                .build();
        Course course = Course.builder()
                .title("Python")
                .credit(6)
                .teacher(teacher)
                .build();
        courseRepository.save(course);
    }

    @Test
    public void findAllPagination(){
        Pageable firstPageWithThreeRecords =
                PageRequest.of(0, 3);
        Pageable secondPageWithTwoRecords =
                PageRequest.of(1, 2);

        List<Course> courses =
                courseRepository.findAll(firstPageWithThreeRecords).getContent();

        long totalElements =
                courseRepository.findAll(firstPageWithThreeRecords).getTotalElements();
        int totalPages =
                courseRepository.findAll(firstPageWithThreeRecords).getTotalPages();
        System.out.println("courses = " + courses);
        System.out.println("totalPages = " + totalPages);
        System.out.println("totalElements = " + totalElements);
    }

    @Test
    public void findAllSorting() {
        Pageable sortByTitle =
                PageRequest.of(0, 2, Sort.by("title"));
        Pageable sortByCreditDesc=
                PageRequest.of(0, 2, Sort.by("credit").descending());
        Pageable sortByTitleAndCreditDesc=
                PageRequest.of(0, 2, Sort.by("credit").descending().and(Sort.by("credit").descending()));

        List<Course> courses
                = courseRepository.findAll(sortByTitle).getContent();
        System.out.println("courses = " + courses);
    }

    @Test
    public void printFindByTitleContaining() {
        Pageable firstPageTenRecords = PageRequest.of(0, 10);
        List<Course> courses =
                courseRepository.findByTitleContaining("IT", firstPageTenRecords).getContent();
        System.out.println("courses = " + courses);
    }


    @Test
    public void SaveCourseWithStudentAndTeacher(){
        Teacher teacher = Teacher.builder()
                .firstName("Morgan")
                .lastName("Le Fae")
                .build();
        Student tristan = Student.builder()
                .firstName("Tristan")
                .lastName("Tam Lin")
                .email("failnaught@camelot.com")
                .build();
        Student gawain = Student.builder()
                .firstName("Gawain")
                .lastName("Tam Lin")
                .email("galantine@camelot.com")
                .build();
        Student lancelot = Student.builder()
                .firstName("Lancelot")
                .lastName("Tam Lin")
                .email("arondight@camelot.com")
                .build();
        Course course = Course.builder()
                .title("AI")
                .credit(12)
                .teacher(teacher)
                .build();

        course.addStudents(tristan);
        course.addStudents(gawain);
        course.addStudents(lancelot);
        courseRepository.save(course);
    }

}