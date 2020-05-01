package com.study.module.jpa.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void findAll(){
        System.out.println(studentRepository.findAll());
    }

    @Test
    public void findById(){
        System.out.println(studentRepository.findById(3));
    }

}