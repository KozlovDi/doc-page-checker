package com.it.one.practice.entity;

import com.it.one.practice.exceptions.ElementNotFoundException;

import java.util.List;

/**
 * Класс отвечающий за набор элементов каждой страницы.
 */

public class PageElements {
    private final List<PageMarker> markers;

    public PageElements(List<PageMarker> markers) {
        this.markers = markers;
    }

    public List<PageMarker> getMarkers() {
        return markers;
    }

    /**
     * findByName находит нужный маркер из списка маркеров по имени.
     * @param name
     * @return PageMarker
     * @throws ElementNotFoundException
     */

    public PageMarker findByName(String name) throws ElementNotFoundException {
        return markers.stream()
                .filter(element -> element.getName().equals(name))
                .findFirst().orElseThrow(() ->
                new ElementNotFoundException("Element doesn't exists"));
    }

    @Override
    public String toString() {
        return "PageElements{" +
                "markers=" + markers +
                '}';
    }
}
