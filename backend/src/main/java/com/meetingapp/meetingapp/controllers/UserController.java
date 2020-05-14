package com.meetingapp.meetingapp.controllers;

import com.meetingapp.meetingapp.models.UserModel;
import com.meetingapp.meetingapp.repository.GraphApi;
import com.meetingapp.meetingapp.serviceimpl.GraphServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = { "http://localhost:8080", "http://localhost:8082","http://localhost:8081", "localhost:8080" })
@RestController
@RequestMapping("/users")
public class UserController {
    final GraphServiceImpl service = new GraphServiceImpl(new GraphApi());

    @GetMapping()
    public ResponseEntity<List<UserModel>> getUsers() {
        return new ResponseEntity(service.getUsers(), HttpStatus.OK);
    }
}
