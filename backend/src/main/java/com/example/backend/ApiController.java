package com.example.backend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ApiController {
    @GetMapping("/")
    public String sayHello() {
        return "Hello from Spring Boot API!";
    }





    /**
     * Store the Data from the front end into the Item collection model, we'll drop the price attribute
     *
     * @param itemCollection json file formatted [{ name: name1, price: price1 }, ...]
     * @return
     */
    @PostMapping("/api/uploard")
    public ResponseEntity<String> storeData(@RequestBody String itemCollection) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(itemCollection);
            Iterator<JsonNode> it = root.elements();
            while (it.hasNext()) {
                JsonNode node = it.next();
                String name = node.get("name").asText();
                // double price = node.get("price").asDouble();
                // ItemCollection item = new ItemCollection(name, price);
                // itemCollection.put(name, item);
            }
            return ResponseEntity.ok("Data stored successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error storing data");
        }
    }

    /**
     * Key is the item name, value is the ArrayList of dates that the item was purchased
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/api/recommendation")
    public String getRecommendation() {
        dataCollector dc = new dataCollector();
        String[] recommendations = dc.getReccomendations();
        // break the array into json [A, B ] => ["A", "B"]
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < recommendations.length; i++) {
            sb.append("\"" + recommendations[i] + "\"");
            if (i != recommendations.length - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

}
