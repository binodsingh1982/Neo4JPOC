package com.javasampleapproach.neo4j.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javasampleapproach.neo4j.model.Student;
import com.javasampleapproach.neo4j.repository.StudentRepository;


@RestController
public class WebController {

	@Autowired
	StudentRepository studentRepository;
	
	/**
	 * Save Student to Neo4j Database
	 * @return
	 */
	@RequestMapping("/addstudents")
	public String addstudents(){
		
		studentRepository.deleteAll();
		
		Student peter = new Student("Peter", "Davis");
		Student jack = new Student("Jack", "Smith  ");
		Student bob = new Student("Bob", "Johnson ");
		
		studentRepository.save(peter);
		studentRepository.save(jack);
		studentRepository.save(bob);

		return "Done";
	}
	
	@RequestMapping("/addclassmatesforpeter")
	public String addClassMateForPeter(){
		Student peter = studentRepository.findByFirstName("Peter");
		Student jack = studentRepository.findByFirstName("Jack");
		Student bob = studentRepository.findByFirstName("Bob");
		
		peter.classMateWith(jack);
		peter.classMateWith(bob);
		studentRepository.save(peter);

		return "Done";
	}
	
	@RequestMapping("/addclassmatesforjack")
	public String addClassMateForJack(){
		Student jack = studentRepository.findByFirstName("Jack");
		Student bob = studentRepository.findByFirstName("Bob");
		
		jack.classMateWith(bob);
		studentRepository.save(jack);

		return "Done";
	}

	
}
