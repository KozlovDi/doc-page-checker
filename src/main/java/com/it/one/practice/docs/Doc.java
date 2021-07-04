package com.it.one.practice.docs;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface Doc {
    public int pageCount();

    public BufferedImage renderPage(int pageNumber) throws IOException;
}
