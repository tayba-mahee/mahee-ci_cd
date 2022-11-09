package com.example.contest.controller;


import com.example.contest.model.Registration;
import com.example.contest.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;

    @PostMapping(value = "/registration")
    public ResponseEntity<Object> register(@RequestBody Registration registration) {
        return registrationService.Register(registration);
    }
}
