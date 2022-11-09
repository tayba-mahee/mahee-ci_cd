package com.example.contest.controller;

import com.example.contest.model.Person;
import com.example.contest.model.Team;
import com.example.contest.repository.PersonRepository;
import com.example.contest.services.SuperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private SuperRepository superRepository;

    @GetMapping(value = "/persons")
    public ResponseEntity<Object> getPerson() {
        return new ResponseEntity<>(personRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping(value = "/persons")
    public ResponseEntity<Object> postUser(@RequestBody Person person) {
        Person newperson = personRepository.save(person);
        return new ResponseEntity<>(newperson, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/persons/{id}")
    public ResponseEntity<Object> getPersonbyID(@PathVariable("id") Long id) {
        return new ResponseEntity<>(personRepository.findById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/persons/by-age")
    public ResponseEntity<Object> getStudentsGroupByAge() {
        return new ResponseEntity(superRepository.ageGroup(), HttpStatus.OK);
    }
}
