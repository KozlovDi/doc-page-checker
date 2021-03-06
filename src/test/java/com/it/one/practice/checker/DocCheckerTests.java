package com.it.one.practice.checker;

import static org.junit.Assert.*;

import com.it.one.practice.config.DocConfig;
import com.it.one.practice.docs.Doc;
import com.it.one.practice.exceptions.ElementNotFoundException;
import com.it.one.practice.loaders.PdfLoader;

import net.sourceforge.tess4j.TesseractException;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;


public class DocCheckerTests {

    private DocChecker checker;
    private DocConfig config;
    private Doc doc;

    @Before
    public void init() throws IOException {
        config = DocConfig.load(new File("src/test/resources/doc.json"));
        doc = new PdfLoader().load(new File("src/test/resources/list.pdf"));
        checker = new DocChecker(doc, config);
    }

    @Test
    public void checkElementsCorrectTest() throws ElementNotFoundException, IOException, TesseractException {
        boolean actual = checker.openPage(0).checkElements("jobName1", "СПИСОК");
        assertTrue(actual);
    }

    @Test
    public void checkElementsWrongTest() throws ElementNotFoundException, IOException, TesseractException {
        boolean actual = checker.openPage(0).checkElements("jobName1", "СПИСО");
        assertFalse(actual);
    }

    @Test(expected = ElementNotFoundException.class)
    public void checkElementsExceptionTest() throws IOException, TesseractException, ElementNotFoundException {
        checker.openPage(0).checkElements("job1", "СПИСОК");
    }

    @Test
    public void compareWithImageCorrectTest() throws IOException {
        boolean actual = checker.openPage(0).compareWithImage(doc.renderPage(0));
        assertTrue(actual);
    }

    @Test
    public void compareWithImageWrongTest() throws IOException {
        Doc document = new PdfLoader().load(new File("src/test/resources/output.pdf"));
        Doc actualDocument = new PdfLoader().load(new File("src/test/resources/docExample.pdf"));
        DocChecker actualChecker = new DocChecker(actualDocument, config);
        boolean actual = actualChecker.openPage(0).compareWithImage(document.renderPage(0));
        assertFalse(actual);
    }

    @Test
    public void compareWithImageWithDifferentSizesTest() throws IOException {
        Doc document = new PdfLoader().load(new File("src/test/resources/docExample.pdf"));
        boolean actual = checker.openPage(0).compareWithImage(document.renderPage(0));
        assertFalse(actual);
    }
}
