package com.it.one.practice.docs;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class PdfDoc implements Doc{

    private PDDocument document;

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
}
