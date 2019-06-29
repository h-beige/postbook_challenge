package com.example.postbookchallenge.domain.entities;

import com.google.gson.annotations.Expose;

public class PostEntity {

    @Expose
    private int userId;

    @Expose
    private int id;

    @Expose
    private String title;

    @Expose
    private String body;

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getKey() {
        return userId + "_" + id;
    }
}
