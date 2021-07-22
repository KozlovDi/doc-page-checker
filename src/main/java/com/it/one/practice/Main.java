package com.it.one.practice;

import com.it.one.practice.checker.DocChecker;
import com.it.one.practice.checker.DocPageChecker;
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

        DocConfig config = DocConfig.load(new File("src/main/resources/markers.json"));

        Doc document = new PdfLoader().load(new File("src/main/resources/docExample.pdf"));

        Doc document2 = new PdfLoader().load(new File("src/main/resources/output.pdf"));

        DocChecker docChecker = new DocChecker(document, config);
//
//        docChecker.openPage(0).compareWithImage(document2.renderPage(0));
//        File file = new File("src/main/resources/compared.png");

        System.out.println(docChecker.openPage(0).checkElements("1", "Место для флтографии"));

//        Tesseract tesseract = new Tesseract();
//        tesseract.setDatapath("src/main/resources/tessdata");
//        tesseract.setLanguage("rus");
//        System.out.println(tesseract.doOCR(document.renderPage(0)));

    }
}
