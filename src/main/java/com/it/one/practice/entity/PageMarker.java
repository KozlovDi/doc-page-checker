package com.it.one.practice.entity;

public class PageMarker {
    private final String name;
    private final boolean ignore;
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public PageMarker(String name, boolean ignore, int x, int y, int width, int height) {
        this.name = name;
        this.ignore = ignore;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
