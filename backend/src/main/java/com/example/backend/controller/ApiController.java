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

    @GetMapping("/api/shopping-list")
    public String getShoppingList() {
        return "Milk, Bread, Eggs";
    }

    @GetMapping("/api/recommendation")
    public String getRecommendation() {
       dataCollector dc = new dataCollector();
       String[] recommendations = dc.getReccomendations();
       return "";
    }

    @PostMapping("/api/upload")
    public String processData(@RequestBody String data) {

        // Process the received data into two arrays, its json [{name: "John", price: 30}, ...]
        String[] items = {};
        String[] prices = {};





        return "Data received: " + data;

    }
}
