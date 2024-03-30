package com.example.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dataCollector;

@RestController
public class ApiController {

    @GetMapping("/")
    public String sayHello() {
        return "Hello from Spring Boot API!";
    }

    @PostMapping("/data")
    public String processData(@RequestBody String data) {
        // Process the received data
        return "Data received: " + data;

    }
}
