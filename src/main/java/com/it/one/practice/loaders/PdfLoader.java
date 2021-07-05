package com.it.one.practice.loaders;

import com.it.one.practice.docs.Doc;
import com.it.one.practice.docs.PdfDoc;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;

public class PdfLoader implements DocLoader {

    @Override
    public Doc load(File file) throws IOException {
        return new PdfDoc(PDDocument.load(file));
    }

}
