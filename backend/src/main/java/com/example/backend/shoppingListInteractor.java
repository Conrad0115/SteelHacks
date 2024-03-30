package com.example.backend;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class shoppingListInteractor {
    String filePath = "resources/csvFiles/ShoppingListTestCopy.csv";

    public void add(String item) throws IOException {
        FileWriter fw = new FileWriter(filePath, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(item + "\n");
        bw.close(); // REMEMBER TO CLOSE
    }

    private List<String> makeList(){
        List<String> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                data.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void remove(String item){
        List<String> data = makeList();
        Iterator<String> iter = data.iterator();
        while (iter.hasNext()) {
            String str = iter.next();
            if (str.equals(item)) {
                // Remove item flagged for deletion
                // from list if found
                iter.remove();
            }
        }
        overwriteCSV(data);
    }

    private void overwriteCSV(List<String> data){
        Iterator<String> iter = data.iterator();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            while (iter.hasNext()) {
                bw.write(iter.next() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

