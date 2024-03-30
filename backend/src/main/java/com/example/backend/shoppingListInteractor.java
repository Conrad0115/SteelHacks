package com.example.backend;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class shoppingListInteractor {
    String fileName = "resources/csvFiles/ShoppingList.csv";

    public void add(String item) throws IOException {
        FileWriter fw = new FileWriter(fileName, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(item);
    }

    private List<List<String>> makeList(){
        List<List<String>> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader fileName)) {
            String line;

            while ((line = avgReader.readLine()) != null) {
                List<String> row = Arrays.asList(line.split(","));
                data.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void remove(String item){
        List<List<String>> data = makeList();
        Iterator<List<String>> outerIterator = data.iterator();
        while (outerIterator.hasNext()) {
            List<String> innerList = outerIterator.next();
            if (innerList.contains(item)) {
                // Remove item flagged for delteion from
                // inner list if found
                outerIterator.remove();
            }
        }

    }



}
