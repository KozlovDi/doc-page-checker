package com.it.one.practice.loaders;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class PdfLoaderTests {

    private File existingFile;
    private File notFile;
    @Before
    public void init() {
        existingFile = new File("src/test/resources/docExample.pdf");
        notFile = new File("Unknown file");
    }

    @Test(expected = IOException.class)
    public void loadExceptionTest() throws IOException {
        new PdfLoader().load(notFile);
    }

    @Test
    public void loadSuccessTest() throws IOException {
        new PdfLoader().load(existingFile);
    }
}
