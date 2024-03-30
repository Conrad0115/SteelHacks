package com.smartgrocerylist;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/** Author: Brandon Hoover
 * 	Date Created: 3/20/23
 *  Function: CONTROLLER CLASS 
 */


@Controller
public class MainController {

  
     /** Explaining RequestMapping, example
      *  When a request is made to the root URL of the application (e.g., localhost:8080), the method index will be called
      *  and when method index is called it returns string index which is the name of an HTML file or a Thymeleaf template named index.html
      *  the spring MVC knows how to handle this and resolves it
      */
    @RequestMapping("/")
    public String index() {

        return "index";
    }
}