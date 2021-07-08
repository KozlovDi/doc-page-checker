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
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class DocPageChecker {

    private final int pageIndex;
    private final PageElements elements;
    private final BufferedImage renderedPage;
    private BufferedImage comparedImage;

    public DocPageChecker(Doc doc, DocConfig config, int pageIndex) throws IOException {
        this.pageIndex = pageIndex;
        this.elements = config.getPageElements(pageIndex);
        this.renderedPage = doc.renderPage(pageIndex);
        //this.comparedImage = this.renderedPage;
    }

    public BufferedImage getComparedImage() {
        return comparedImage;
    }

    private String recognizeMarker(PageMarker marker) throws TesseractException {
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("src/main/resources/tessdata");
        tesseract.setLanguage("rus");
        return tesseract.doOCR(renderedPage,
                new Rectangle(marker.getX(), marker.getY(), marker.getWidth(), marker.getHeight())).trim();
    }

    public boolean checkElements(String markerName, String expectedText) throws ElementNotFoundException, TesseractException {
        String marker = recognizeMarker(elements.findByName(markerName));
        return marker.equals(expectedText);
    }

    public boolean compareWithImage(BufferedImage image) {
        comparedImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        RescaleOp brighterOp = new RescaleOp(0.5f, 0, null);
        comparedImage = brighterOp.filter(image, null);
        List<PageMarker> uncheckableMarkers =  elements.getMarkers().stream()
                                                .filter(elem -> elem.isIgnore())
                                                .collect(Collectors.toList());
        List<PageMarker> checkableMarkers =  elements.getMarkers().stream()
                                                .filter(elem -> !elem.isIgnore())
                                                .collect(Collectors.toList());

        List<PageMarker> wrongMarkers = new ArrayList<>();

        if (image.getWidth() == this.renderedPage.getWidth() && image.getHeight() == this.renderedPage.getHeight()) {
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    for (PageMarker uncheckableMarker : uncheckableMarkers) {
                        while ((x >= uncheckableMarker.getX() && x <= uncheckableMarker.getX() + uncheckableMarker.getWidth()) && (y >= uncheckableMarker.getY() && y <= uncheckableMarker.getY() + uncheckableMarker.getHeight())){
                            y++;
                        }
                    }
                    if(image.getRGB(x, y) != this.renderedPage.getRGB(x, y)){
                        for (PageMarker checkableMarker : checkableMarkers) {
                            if((x >= checkableMarker.getX() && x <= checkableMarker.getX() + checkableMarker.getWidth()) && (y >= checkableMarker.getY() && y <= checkableMarker.getY() + checkableMarker.getHeight())){
                                if(!wrongMarkers.contains(checkableMarker)){
                                    wrongMarkers.add(checkableMarker);
                                }
                            }
                        }
                        //System.out.println("Images are not equal");
                        //return false;
                    }
                }
            }

            for(PageMarker wrongMarker : wrongMarkers){
                for (int x = wrongMarker.getX(); x < wrongMarker.getX() + wrongMarker.getWidth(); x++) {
                    for (int y = wrongMarker.getY(); y < wrongMarker.getY() + wrongMarker.getHeight(); y++) {
                        comparedImage.setRGB(x, y, image.getRGB(x, y));
                    }
                }
            }

            if(wrongMarkers.size() == 0){
                System.out.println("Images are equal");
                return true;
            }
            else{
                System.out.println("Images are not equal");
                return false;
            }
        }
        else {
            //System.out.println("Images have different sizes");
            return false;
        }
    }
}
