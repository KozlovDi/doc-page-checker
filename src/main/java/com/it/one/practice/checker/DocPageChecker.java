package com.it.one.practice.checker;

import com.it.one.practice.config.DocConfig;
import com.it.one.practice.docs.Doc;
import com.it.one.practice.entity.PageElements;
import com.it.one.practice.entity.PageMarker;
import com.it.one.practice.exceptions.ElementNotFoundException;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DocPageChecker {

    private final int pageIndex;
    private final PageElements elements;
    private final BufferedImage renderedPage;

    public DocPageChecker(Doc doc, DocConfig config, int pageIndex) throws IOException {
        this.pageIndex = pageIndex;
        this.elements = config.getPageElements(pageIndex);
        this.renderedPage = doc.renderPage(pageIndex);
    }

    private String recognizeMarker(PageMarker marker) throws TesseractException {
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("src/main/resources/tessdata");
        tesseract.setLanguage("rus");
        return tesseract.doOCR(renderedPage, new Rectangle(marker.getX(), marker.getY(), marker.getWidth(), marker.getHeight()));
    }

    public void checkElements(String markerName, String expectedText) throws ElementNotFoundException, TesseractException {
        String marker = recognizeMarker(elements.findByName(markerName)).trim();
        System.out.println(marker.equals(expectedText) ? "Marker is correct" : "Marker is wrong");
    }

    public void compareWithImage(BufferedImage image) {
        List<PageMarker> uncheckableMarkers =  elements.getMarkers().stream()
                                                .filter(elem -> !elem.isIgnore())
                                                .collect(Collectors.toList());

        if (image.getWidth() == this.renderedPage.getWidth() && image.getHeight() == this.renderedPage.getHeight()) {
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    for (PageMarker uncheckableMarker : uncheckableMarkers) {
                        System.out.println("Before: " + y);
                        while ((x >= uncheckableMarker.getX() && x <= uncheckableMarker.getX() + uncheckableMarker.getWidth()) && (y >= uncheckableMarker.getY() && y <= uncheckableMarker.getY() + uncheckableMarker.getHeight())) {
                            y++;
                        }
                        System.out.println("After: " + y);
                    }
                    if (image.getRGB(x, y) != this.renderedPage.getRGB(x, y)){
                        System.out.println("Images are not equal");
                        return;
                    }
                }
            }
            System.out.println("Images are equal");
        }
        else {
            System.out.println("Images have different sizes");
        }
    }
}
