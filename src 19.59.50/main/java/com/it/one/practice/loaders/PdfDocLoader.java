package com.it.one.practice.loaders;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;

import com.it.one.practice.docs.Doc;
import com.it.one.practice.docs.PdfDoc;

public class PdfDocLoader implements DocLoader {

    public PdfDocLoader() {

    }

    @Override
    public Doc load(File file) {
        PDDocument doc = null;
        try {
            doc = PDDocument.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new PdfDoc(doc);
    }
}
