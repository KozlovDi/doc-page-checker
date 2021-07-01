package com.it.one.practice;

import com.it.one.practice.config.DocConfig;
import com.it.one.practice.exceptions.ElementNotFoundException;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ElementNotFoundException {
        DocConfig.load(new File("src\\main\\resources\\doc.json"));
    }
}
