package com.it.one.practice.docs;

import org.apache.pdfbox.pdmodel.PDDocument;

import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;

import static org.junit.Assert.*;

public class PdfDocTests {

    private File onePagePdf;
    private File twoPagesPdf;

    @Before
    public void init() {
        onePagePdf = new File("src/test/resources/list.pdf");
        twoPagesPdf = new File("src/test/resources/docExample.pdf");
    }

    @Test
    public void pageCountOnePageTest() throws IOException {
        PdfDoc doc = new PdfDoc(PDDocument.load(onePagePdf));
        assertEquals(1, doc.pageCount());
    }

    @Test
    public void pageCountTwoPagesTest() throws IOException {
        PdfDoc doc = new PdfDoc(PDDocument.load(twoPagesPdf));
        assertEquals(2, doc.pageCount());
    }

    /*@Test
    public void renderPageCompareResultTest() throws IOException {
        PdfDoc doc = new PdfDoc(PDDocument.load(onePagePdf));
        BufferedImage renderedPage = doc.renderPage(0);

        File actual = new File("src/test/resources/actual.png");
        ImageIO.write(renderedPage, "png", actual);

        File expected = new File("src/test/resources/image.png");
        assertTrue(compare(expected, actual));
    }

    boolean compare(File file, File comparedFile) throws IOException {
        byte[] data = new FileInputStream(file).readAllBytes();
        byte[] comparedData = new FileInputStream(comparedFile).readAllBytes();
        return Arrays.equals(data, comparedData);
    }*/
}
