package com.meetingapp.meetingapp.controllers;

import com.meetingapp.meetingapp.helpers.AuthenticationConstants;
import com.meetingapp.meetingapp.models.ApplicationUser;
import com.meetingapp.meetingapp.models.Room;
import com.meetingapp.meetingapp.serviceimpl.ExchangeServiceImpl;
import com.meetingapp.meetingapp.services.AuthenticationService;
import microsoft.exchange.webservices.data.core.ExchangeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = { "http://localhost:8082","http://localhost:8080","http://localhost:8081" })
public class AuthenticationController {

    private AuthenticationService authenticationRepository;

    public AuthenticationController(AuthenticationService authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody ApplicationUser body, HttpServletResponse response) {
        String jwt = authenticationRepository.authenticateLogin(body.getUsername(), body.getPassword());
        response.addHeader(AuthenticationConstants.HEADER_STRING, jwt);

        if(jwt != null) return new ResponseEntity<>(jwt, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
