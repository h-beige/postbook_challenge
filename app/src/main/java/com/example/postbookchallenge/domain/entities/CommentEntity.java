package com.example.postbookchallenge.domain.entities;

import com.google.gson.annotations.Expose;

public class CommentEntity {

    @Expose
    private int postId;

    @Expose
    private int id;

    @Expose
    private String name;

    @Expose
    private String email;

    @Expose
    private String body;

    public int getPostId() {
        return postId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBody() {
        return body;
    }
}
