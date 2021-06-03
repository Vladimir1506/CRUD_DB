package com.vladimir1506.crud_db.model;

import java.util.Date;

public class Post {
    private Long id;
    private String content;
    private Date created;
    private Date updated;

    public Post(Long id, String content) {
        this.id = id;
        this.content = content;
        this.created = new Date();
        this.updated = created;
    }

    public String toString() {
        return "\n" + this.id + ". " + this.content + "\nДата создания: " + this.created + "\nДата изменения: " + this.updated;
    }

    public String write() {
        return this.id + ". " + this.content + " Дата создания: " + this.created + " Дата изменения: " + this.updated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}

