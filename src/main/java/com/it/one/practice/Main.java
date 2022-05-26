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

        // Пример получения конфига документа.
        DocConfig config = DocConfig.load(new File("src/main/resources/markers.json"));

        // Привер считывания PDF документа.
        Doc document1 = new PdfLoader().load(new File("src/main/resources/docExample.pdf"));
        Doc document2 = new PdfLoader().load(new File("src/main/resources/output.pdf"));

        // Пример связывания конфига с документов.
        DocChecker docChecker = new DocChecker(document1, config);

        // Пример сравнения изображений 2-х страниц.
        docChecker.openPage(0).compareWithImage(document2.renderPage(0));

        // Пример проверки документа по маркеру.
        System.out.println(docChecker.openPage(0).checkElements("1", "место для фотографии"));

    }
}
