package com.it.one.practice.docs;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class PdfDoc implements Doc{

    private final PDDocument document;

    public PdfDoc(PDDocument pdfDocument){
        this.document = pdfDocument;
    }

    @Override
    public int pageCount() {
        return this.document.getNumberOfPages();
    }

    @Override
    public BufferedImage renderPage(int pageNumber) throws IOException {
        PDFRenderer pdfRenderer = new PDFRenderer(this.document);
        return pdfRenderer.renderImageWithDPI(pageNumber, 300);
    }

    @Override
    public boolean equals(Object anotherDoc) {
        if (this == anotherDoc) {
            return true;
        }
        if (anotherDoc == null || getClass() != anotherDoc.getClass()) {
            return false;
        }
        PdfDoc pdfDoc = (PdfDoc) anotherDoc;
        return Objects.equals(document, pdfDoc.document);
    }

    @Override
    public int hashCode() {
        return Objects.hash(document);
    }
}
