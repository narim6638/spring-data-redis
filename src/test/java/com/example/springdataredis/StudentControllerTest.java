package com.example.springdataredis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentControllerTest {

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    public void setUp() {
        studentRepository.deleteAll();
    }

    @Test
    public void saveAndRetrieve() {
        String name = "John Doe";
        Student student = new Student("Eng2015001", name, Student.Gender.MALE, 1);
        studentRepository.save(student);

        Student retrievedStudent = studentRepository.findById("Eng2015001").get();
        Assertions.assertEquals(name, retrievedStudent.getName());
    }

    @Test
    public void saveAndUpdate() {

        Student student = new Student("Eng2015001", "John Doe", Student.Gender.MALE, 1);
        studentRepository.save(student);

        Student retrievedStudent = studentRepository.findById("Eng2015001").get();
        System.out.println("retrievedStudent = " + retrievedStudent);

        String newName = "Richard Watson";
        retrievedStudent.setName(newName);
        Student saved = studentRepository.save(retrievedStudent);
        Assertions.assertEquals(newName, saved.getName());
    }

    @Test
    public void saveAndDelete() {

        Student student = new Student("Eng2015001", "John Doe", Student.Gender.MALE, 1);
        studentRepository.save(student);

        List<Student> students = new ArrayList<>();
        studentRepository.findAll().forEach(students::add);
        Assertions.assertEquals(1, students.size());

        studentRepository.deleteById(student.getId());

        students.clear();
        studentRepository.findAll().forEach(students::add);
        Assertions.assertEquals(0, students.size());
    }

    @Test
    public void saveAndFindAll() {

        int SIZE = 2;
        for (int i = 0; i < SIZE; i++) {
            Student cloneStudent = new Student("STUDENT-" + i, "John Doe", Student.Gender.MALE, 1);
            studentRepository.save(cloneStudent);
        }

        List<Student> students = new ArrayList<>();
        studentRepository.findAll().forEach(students::add);
        Assertions.assertEquals(SIZE, students.size());
    }
}