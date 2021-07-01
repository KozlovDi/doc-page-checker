package com.it.one.practice.entity;

public class PageMarker {
    private String name;
    private boolean ignore;
    private int x;
    private int y;
    private int width;
    private int height;

    public PageMarker(){
    }

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
