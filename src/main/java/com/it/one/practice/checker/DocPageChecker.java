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

/**
 * Класс отвечающий за проверку страницы документа/
 */

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

    /**
     * recognizeMarker определяет текст находящийся внутри маркера.
     * @param marker
     * @return String
     * @throws TesseractException
     */

    private String recognizeMarker(PageMarker marker) throws TesseractException {
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("src/main/resources/tessdata");
        tesseract.setLanguage("rus");
        double ratio = 300.0 / dpi;
        Rectangle coords = new Rectangle((int)(marker.getX() * ratio), (int)(marker.getY() * ratio), (int)(marker.getWidth() * ratio), (int)(marker.getHeight() * ratio));
        System.out.println(tesseract.doOCR(renderedPage, coords).trim());
        return tesseract.doOCR(renderedPage, coords).trim();
    }

    /**
     * checkElements проверяет совпало ли содержимое маркера с ожидаемым результатом.
     * @param markerName
     * @param expectedText
     * @return  boolean
     * @throws ElementNotFoundException
     * @throws TesseractException
     */

    public boolean checkElements(String markerName, String expectedText) throws ElementNotFoundException, TesseractException {
        String marker = recognizeMarker(elements.findByName(markerName));
        return marker.equals(expectedText);
    }

    /**
     * compareWithImage сравнивает 2 картинки и отображает различия, если они есть.
     * @param image
     * @return boolean
     * @throws IOException
     */

    public boolean compareWithImage(BufferedImage image) throws IOException {
        comparedImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        RescaleOp brighterOp = new RescaleOp(0.5f, 0, null);
        comparedImage = brighterOp.filter(image, null);
        List<PageMarker> uncheckableMarkers =  elements.getMarkers().stream()
                .filter(PageMarker::isIgnore)
                .collect(Collectors.toList());

        boolean isEqual = true;

        if (image.getWidth() == this.renderedPage.getWidth() && image.getHeight() == this.renderedPage.getHeight()) {
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    for (PageMarker uncheckableMarker : uncheckableMarkers) {
                        while ((x >= uncheckableMarker.getX() && x <= uncheckableMarker.getX() + uncheckableMarker.getWidth()) && (y >= uncheckableMarker.getY() && y <= uncheckableMarker.getY() + uncheckableMarker.getHeight())){
                            y++;
                        }
                    }
                    if(image.getRGB(x, y) != this.renderedPage.getRGB(x, y)){
                        for(int i = x - 10; i <= x + 10; i++){
                            for(int j = y - 10; j <= y + 10; j++){
                                if(i < 0)
                                    i = 0;
                                else if(i > image.getWidth())
                                    i = image.getWidth();
                                if(j < 0)
                                    j = 0;
                                else if(j > image.getHeight())
                                    j = image.getHeight();
                                comparedImage.setRGB(i, j, image.getRGB(i, j));
                                isEqual = false;
                            }
                        }
                    }
                }
            }

            ImageIO.write(comparedImage, "png", new File("src/main/resources/comparedImages/page " + pageIndex + ".png"));

            if(isEqual){
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

    /**
     * checkBarcode ставнивает значение считываемого по маркеру Barcode с ожидаемым результатом.
     * @param markerName
     * @param expectedText
     * @return boolean
     * @throws NotFoundException
     * @throws ElementNotFoundException
     */

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