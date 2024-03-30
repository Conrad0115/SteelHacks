package com.example.backend;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class writeData {

    String samPath = "backend/src/main/resources/csvFiles/sampledata.csv";
    String avgPath = "backend/src/main/resources/csvFiles/sampleAverage.csv";

    public void process (String Result) {
        String[][] receipt = new String[Result.length()/2][2];
        int counter = 0;
        for(int i =0; i< receipt.length; i++){
            for(int j =0; j<2; j++){
                receipt[i][j] = Result.split(",")[counter];
                System.out.println(receipt[i][j]);
                counter ++;
            }
        }
        // write all data from receipt separated by commas to sample csv
        try (BufferedWriter samWriter = new BufferedWriter(new FileWriter(samPath,true))) {
            for (String[] row : receipt) {
                samWriter.write(row[0] +"," + getDate()+","+row[1]);

            }
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
        // read the avg csv to see if item is contained there
        try (BufferedReader avgReader = new BufferedReader(new FileReader(avgPath))) {
            for (String[] row : receipt) {
                if (!findItem(avgReader, row[0])) {
                    List<String> toWrite = new ArrayList<>();
                    toWrite.add(row[0]);
                    toWrite.add((row[1]));
                    firstWrite(toWrite);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
        makeAvgs make = new makeAvgs(getDate());
    }

    private boolean findItem(BufferedReader avgReader, String item) throws IOException {

        String line;

        while ((line = avgReader.readLine()) != null) {
            List<String> curr = Arrays.asList(line.split(","));
            String currItem = curr.get(0);
            if (item.equals(currItem)) {
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