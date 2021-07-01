package com.it.one.practice;

import com.it.one.practice.config.DocConfig;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(DocConfig.load(new File("src/main/resources/doc.json")));

    }
}
