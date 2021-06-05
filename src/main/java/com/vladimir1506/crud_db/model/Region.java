package com.vladimir1506.crud_db.model;

public class Region {
    private Long id;
    private final String name;

    public Region(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
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
