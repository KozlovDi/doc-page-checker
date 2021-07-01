package com.it.one.practice.entity;

import com.it.one.practice.exceptions.ElementNotFoundException;

import java.util.List;

public class PageElements {
    private List<PageMarker> markers;

    public PageElements (){}

    public PageElements(List<PageMarker> markers) {
        this.markers = markers;
    }

    public List<PageMarker> getMarkers() {
        return markers;
    }


    public PageMarker findByName(String name) throws ElementNotFoundException {
        return markers.stream()
                .filter(element -> element.getName().equals(name))
                .findFirst().orElseThrow(() ->
                new ElementNotFoundException("Element doesn't exists"));
    }
}
