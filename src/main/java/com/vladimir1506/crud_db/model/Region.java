package com.vladimir1506.crud_db.model;

public class Region {
    private final Long id;
    private String name;

    public Region(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String toString() {
        return id + ". " + name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }
}
