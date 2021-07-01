package com.it.one.practice;

import com.it.one.practice.config.DocConfig;
<<<<<<< HEAD
import com.it.one.practice.exceptions.ElementNotFoundException;

import java.io.File;
import java.io.IOException;
=======

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(DocConfig.load(new File("src/main/resources/doc.json")));
>>>>>>> f84593e106e9a700bafdb466ef41f7e22e72d259

public class Main {
    public static void main(String[] args) throws IOException, ElementNotFoundException {
        DocConfig.load(new File("src\\main\\resources\\doc.json"));
    }
}
