package com.jpatest.springjpa.repository;

import com.jpatest.springjpa.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    @Query(
            value = "SELECT * FROM tbl_student s " +
                    "WHERE s.email_address = :email",
            nativeQuery = true
    )
    Student getStudentByEmailAddressNativeNamedParam(
            @Param("email") String email);

    @Modifying
    @Transactional
    @Query(
            value = "UPDATE tbl_student SET first_name = :firstName WHERE email_address = :email",
            nativeQuery = true
    )
    int updateStudentNameByEmail(@Param("firstName") String firstName, @Param("email") String email);
}
