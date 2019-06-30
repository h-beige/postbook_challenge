package com.example.postbookchallenge.domain.entities;

import com.google.gson.annotations.Expose;

import androidx.annotation.VisibleForTesting;

public class UserEntity {

    @Expose
    private String id;

    public String getId() {
        return id;
    }

    @VisibleForTesting()
    public UserEntity setId(String id) {
        this.id = id;
        return this;
    }
}
