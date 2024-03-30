package com.example.backend;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class writeData {

    String samPath = "resources/csvFiles/sampledata.csv";
    String avgPath = "resources/csvFiles/ShoppingList.csv";

    public void process (List<List<String>> receipt) {
        // write all data from receipt separated by commas to sample csv
        try (BufferedWriter samWriter = new BufferedWriter(new FileWriter(samPath))) {
            for (List<String> row : receipt) {
                samWriter.write(String.join(",", row));
                samWriter.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
        // read the avg csv to see if item is contained there
        try (BufferedReader avgReader = new BufferedReader(new FileReader(avgPath))) {
            for (List<String> row : receipt) {
                if (!findItem(avgReader, row)) {
                    firstWrite(row);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
        // call makeAVG
    }

    private boolean findItem(BufferedReader avgReader, List<String> row) throws IOException {
        String name = row.get(0);
        String line;

        while ((line = avgReader.readLine()) != null) {
            List<String> curr = Arrays.asList(line.split(","));
            String currItem = curr.get(0);
            if (name.equals(currItem)) {
                return true;
            }
        }
        return false;
    }

    public void firstWrite (List<String> row) {
        String item = row.get(0);
        String fDate = getDate();
        String lDate = "/0";
        // set avg price as the price of the item since this is the first occurrence
        String avg = row.get(3);
        String num = "1"; // num purchased is 1 bc this is the first occurrence

        try (BufferedWriter avgWriter = new BufferedWriter(new FileWriter(avgPath))) {
            // Write data points to csv concatenated with commas
            avgWriter.write(item + "," + fDate + "," + lDate + "," + avg +"," + num + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    private String getDate() {
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(currentDate);
    }
}