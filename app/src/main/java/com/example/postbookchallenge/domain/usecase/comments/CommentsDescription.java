package com.example.postbookchallenge.domain.usecase.comments;

import com.example.postbookchallenge.domain.entities.CommentEntity;

import java.util.List;

public class CommentsDescription {

    private List<CommentEntity> comments;

    public List<CommentEntity> getComments() {
        return comments;
    }

    public CommentsDescription setComments(List<CommentEntity> comments) {
        this.comments = comments;
        return this;
    }
}
