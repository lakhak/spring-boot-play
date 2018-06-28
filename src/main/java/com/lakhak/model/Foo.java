package com.lakhak.model;

public class Foo {
    private final Long id;
    private final Bar bar;

    public Foo(Long id, Bar bar) {
        this.id = id;
        this.bar = bar;
    }

    public Long getId() {
        return id;
    }

    public Bar getBar() {
        return bar;
    }
}
