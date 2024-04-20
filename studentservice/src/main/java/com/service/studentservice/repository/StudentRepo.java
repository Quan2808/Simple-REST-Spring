package com.service.studentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.service.studentservice.entities.Student;

public interface StudentRepo extends JpaRepository<Student, String>{

}
