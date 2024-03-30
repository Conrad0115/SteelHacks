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
    public void remove(String item){
        

    }
}
