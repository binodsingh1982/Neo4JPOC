package com.javasampleapproach.neo4j.repository;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.javasampleapproach.neo4j.model.Student;

public interface StudentRepository extends GraphRepository<Student> {

    Student findByFirstName(String firstName);
    Student findByLastName(String lastName);
}