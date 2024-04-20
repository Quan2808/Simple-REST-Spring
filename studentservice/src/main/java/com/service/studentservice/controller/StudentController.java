package com.service.studentservice.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RestController;
import com.service.studentservice.entities.Student;
import com.service.studentservice.repository.StudentRepo;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    StudentRepo repo;

    @GetMapping()
    public List<Student> getStudents() {
        return repo.findAll();
    }

    @GetMapping("/code={code}")
    public ResponseEntity<Student> getStudent(@PathVariable String code) {
        Student existingStudent = repo.findById(code).orElse(null);

        if (existingStudent != null) {
            return ResponseEntity.ok(existingStudent);
        } else {
            Student emptyStudent = new Student();
            emptyStudent.setName("Student not found");
            emptyStudent.setCode(code);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(emptyStudent);
        }
    }

    @PostMapping()
    public ResponseEntity<String> postStudent(@RequestBody Student s) {
        Student existingStudent = repo.findById(s.getCode()).orElse(null);

        if (existingStudent == null) {
            repo.save(s);
            return ResponseEntity.ok("Student with code \"" + s.getCode() + "\" has been created.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Student with code \"" + s.getCode() + "\" already exists.");
        }
    }

    @PutMapping("/{code}")
    public ResponseEntity<String> putStudent(@RequestBody Student s, @PathVariable String code) {
        Student findStudent = repo.findById(code).orElse(null);

        if (findStudent != null) {
            findStudent.setName(s.getName());
            findStudent.setYearOfBirth(s.getYearOfBirth());
            findStudent.setPhone(s.getPhone());
            repo.save(findStudent);
            return ResponseEntity.ok("Student with code \"" + code + "\" has been updated.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Student with code \"" + code + "\" not found.");
        }
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<String> removeStudent(@PathVariable String code) {
        Student findStudent = repo.findById(code).orElse(null);

        if (findStudent != null) {
            repo.delete(findStudent);
            return ResponseEntity.ok("Student with code \"" + code + "\" has been deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Student with code \"" + code + "\" not found.");
        }
    }

    @ControllerAdvice
    public class GlobalExceptionHandler {
        @ExceptionHandler({ Exception.class })
        public ResponseEntity<String> handleException(Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ex.getMessage());
        }

        @ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
        public ResponseEntity<String> handleMethodNotAllowedException(HttpRequestMethodNotSupportedException ex) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                    .body(ex.getMessage());
        }
    }

}
