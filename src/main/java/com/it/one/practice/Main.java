package com.it.one.practice;

import com.it.one.practice.config.DocConfig;
import com.it.one.practice.docs.Doc;
import com.it.one.practice.loaders.PdfLoader;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;
import java.io.IOException;

public class Main {

    private static final ClassLoader loader = Main.class.getClassLoader();

    public static void main(String[] args) throws IOException, TesseractException {
        DocConfig config = DocConfig.load(new File(loader.getResource("doc.json").getPath()));

        Doc document = new PdfLoader().load(new File(loader.getResource("test.pdf").getPath()));


        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("src/main/resources/tessdata");
        tesseract.setLanguage("eng");
        System.out.println(tesseract.doOCR(document.renderPage(0)));
    }
}
