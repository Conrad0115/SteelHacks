package com.example.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    /**
     * a map that stores the name of the item and the dates it was purchased
     */
//    public static Map<String, ArrayList<Date>> itemDates = new HashMap<>();

    @GetMapping("/")
    public String sayHello() {
        return "Hello from Spring Boot API!";
    }


//    @GetMapping("/api/recommendation")
//    public String getRecommendation() {
//        dataCollector dc = new dataCollector();
//        String[] recommendations = dc.getReccomendations();
//        return Arrays.toString(recommendations);
//    }

    /**
     * @param data json file
     * @return ArrayList[] [0]: ArrayList<grocery> [1]: ArrayList<price>
     */
//    @PostMapping("/api/upload")
//    public String processData(@RequestBody String data) {
//        // push data in to arraylist
//        ObjectMapper om = new ObjectMapper();
//        try {
//            JsonNode jn = om.readTree(data);
//            for (JsonNode node : jn) {
//                String itemName = node.get("name").asText();
//                // add item to itemDates
//                if (itemDates.containsKey(itemName)) {
//                    itemDates.get(itemName).add(new Date());
//                } else {
//                    ArrayList<Date> dates = new ArrayList<>();
//                    dates.add(new Date());
//                    itemDates.put(itemName, dates);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ("data received");
//    }


}
