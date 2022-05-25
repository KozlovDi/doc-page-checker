package com.it.one.practice.config;

import com.it.one.practice.entity.PageElements;

/**
 * Класс отвечающий за конфигурацию каждой страницы.
 */

public class PageConfig {

    private final PageElements elements;

    public PageConfig(PageElements elements){
        this.elements = elements;
    }

    public PageElements getElements() {
        return elements;
    }

    @Override
    public String toString() {
        return "PageConfig{" +
                "elements=" + elements +
                '}';
    }
}
