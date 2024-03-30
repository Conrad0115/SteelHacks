package com.example.backend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
    @PostMapping("/api/upload")
    @CrossOrigin
    public ResponseEntity<String> storeData(@RequestBody String itemCollection) {
        System.out.println(itemCollection);
        ObjectMapper mapper = new ObjectMapper();
        String path = "src/main/resources/csvFiles/temp.csv";
        try {
            JsonNode root = mapper.readTree(itemCollection);
            Iterator<JsonNode> it = root.elements();
            JsonNode node = it.next();
            node = it.next();
            while (it.hasNext()) {
                node = it.next();
                if(node == null){
                    continue;
                }
                String name = node.get("name").asText();
                double price = node.get("price").asDouble();

                try (BufferedWriter samWriter = new BufferedWriter(new FileWriter(path))) {
                    samWriter.write(name+","+price);
                    samWriter.newLine();


                } catch (IOException e) {
                    System.err.println("Error writing to CSV file: " + e.getMessage());
                }


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
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/api/getList")
    public String getsList() {
        dataCollector dc = new dataCollector();
        String[] shoppingList = dc.getShoppingList();
        // break the array into json [A, B ] => ["A", "B"]
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < shoppingList.length; i++) {
            sb.append("{"+"\"" + shoppingList[i] + "\""+"}");
            if (i != shoppingList.length - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

}
