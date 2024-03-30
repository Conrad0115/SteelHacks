package com.example;

import java.io.File;

import net.sourceforge.tess4j.*;

public class App {
    public static void main(String[] args) {
        Tesseract tesseract = new Tesseract();
        try {

            tesseract.setDatapath("./");

            // the path of your tess data folder
            // inside the extracted file
            String text = tesseract.doOCR(new File("receipt.jpg"));

            // path of your image file
            System.out.print(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}