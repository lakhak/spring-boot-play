package com.lakhak.model;

public class Bar {
    private final Long id;
    private final String field;

    public Bar(Long id, String field) {
        this.id = id;
        this.field = field;
    }

    public Long getId() {
        return id;
    }

    public String getField() {
        return field;
    }
}
