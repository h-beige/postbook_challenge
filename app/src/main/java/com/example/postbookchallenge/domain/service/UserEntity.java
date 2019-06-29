package com.example.postbookchallenge.domain.service;

import com.google.gson.annotations.Expose;

public class UserEntity {

    @Expose
    private String id;

    @Expose
    private String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
