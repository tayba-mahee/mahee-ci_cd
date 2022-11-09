package com.example.contest.controller;

import com.example.contest.services.SuperRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class MyController {

    @Autowired
    private SuperRepository superRepository;

    @SneakyThrows
    @GetMapping(value = "/populate")
    public ResponseEntity populate() {
        superRepository.populate();
        return new ResponseEntity(HttpStatus.OK);
    }

}
