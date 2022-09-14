package com.epam.esm.util;

public class PageRequest {

    private int page;
    private int size;
    private Sort sort;

    {
        page = 0;
        size = 20;
    }

    public PageRequest() {
    }

    public PageRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public PageRequest(int page, int size, Sort sort) {
        this.page = page;
        this.size = size;
        this.sort = sort;
    }

    public Sort getSort() {
        return sort;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }
}
