package com.it.one.practice;

import com.it.one.practice.checker.DocChecker;
import com.it.one.practice.config.DocConfig;

import com.it.one.practice.docs.Doc;
import com.it.one.practice.exceptions.ElementNotFoundException;
import com.it.one.practice.loaders.PdfLoader;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException, TesseractException, ElementNotFoundException {

        DocConfig config = DocConfig.load(new File("src/main/resources/doc.json"));

        Doc document = new PdfLoader().load(new File("src/main/resources/docExample.pdf"));

        DocChecker docChecker = new DocChecker(document, config);

        //docChecker.openPage(0).compareWithImage(document1.renderPage(0));

        docChecker.openPage(0).checkElements("jobName1", "СПИСОК");

//        Tesseract tesseract = new Tesseract();
//        tesseract.setDatapath("src/main/resources/tessdata");
//        tesseract.setLanguage("rus");
//        System.out.println(tesseract.doOCR(document.renderPage(0)));

    }
}
