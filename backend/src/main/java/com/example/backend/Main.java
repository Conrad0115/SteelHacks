package com.example.backend;

import java.util.Arrays;

public class Main {
    public static void main(String[] args)  {
        dataCollector stuff = new dataCollector();
        String[] toPrint = stuff.getReccomendations();
        System.out.println(Arrays.toString(toPrint));
    }
}
