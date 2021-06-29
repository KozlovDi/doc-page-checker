package com.it.one.practice.docs;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface Doc {
    public Integer pageCount();

    public BufferedImage renderPage(Integer pageNumber) throws IOException;
}
