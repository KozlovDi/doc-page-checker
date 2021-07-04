package com.it.one.practice.loaders;

import com.it.one.practice.docs.Doc;
import com.it.one.practice.docs.PdfDoc;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;

public class PdfLoader implements DocLoader {

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
