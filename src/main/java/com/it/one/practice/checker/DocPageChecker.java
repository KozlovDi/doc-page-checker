package com.it.one.practice.checker;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.it.one.practice.config.DocConfig;
import com.it.one.practice.docs.Doc;
import com.it.one.practice.entity.PageElements;
import com.it.one.practice.entity.PageMarker;
import com.it.one.practice.exceptions.ElementNotFoundException;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class DocPageChecker {

    private final int pageIndex;
    private final PageElements elements;
    private final BufferedImage renderedPage;
    private BufferedImage comparedImage;
    private final int dpi;

    DocPageChecker(Doc doc, DocConfig config, int pageIndex) throws IOException {
        this.pageIndex = pageIndex;
        this.elements = config.getPageElements(pageIndex);
        this.dpi = config.getDpi();
        this.renderedPage = doc.renderPage(pageIndex);
    }

    public BufferedImage getComparedImage() {
        return comparedImage;
    }

    private String recognizeMarker(PageMarker marker) throws TesseractException {
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("src/main/resources/tessdata");
        tesseract.setLanguage("rus");
        double ratio = 300.0 / dpi;
        Rectangle coords = new Rectangle((int)(marker.getX() * ratio), (int)(marker.getY() * ratio), (int)(marker.getWidth() * ratio), (int)(marker.getHeight() * ratio));
        System.out.println(tesseract.doOCR(renderedPage, coords).trim());
        return tesseract.doOCR(renderedPage, coords).trim();
    }

    public boolean checkElements(String markerName, String expectedText) throws ElementNotFoundException, TesseractException {
        String marker = recognizeMarker(elements.findByName(markerName));
        return marker.equals(expectedText);
    }

    public boolean compareWithImage(BufferedImage image) throws IOException {
        comparedImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        RescaleOp brighterOp = new RescaleOp(0.5f, 0, null);
        comparedImage = brighterOp.filter(image, null);
        List<PageMarker> uncheckableMarkers =  elements.getMarkers().stream()
                .filter(PageMarker::isIgnore)
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

            ImageIO.write(comparedImage, "png", new File("src/main/resources/compared.png"));

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
            System.out.println("Images have different sizes");
            return false;
        }
    }

    public boolean checkBarcode(String markerName, String expectedText) throws NotFoundException, ElementNotFoundException {
        PageMarker qrCodeMarker = elements.findByName(markerName);
        BufferedImage qrCodeImage = renderedPage
                .getSubimage(qrCodeMarker.getX(), qrCodeMarker.getY(),
                        qrCodeMarker.getWidth(), qrCodeMarker.getHeight());

        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
                new BufferedImageLuminanceSource(qrCodeImage)));
        return new MultiFormatReader().decode(binaryBitmap).getText().equals(expectedText);
    }
}
