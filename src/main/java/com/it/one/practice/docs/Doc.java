package com.it.one.practice.docs;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface Doc {
    int pageCount();
    BufferedImage renderPage(int pageNumber) throws IOException;
}
