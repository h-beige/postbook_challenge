package com.example.postbookchallenge.presentation.comments;

import com.example.postbookchallenge.domain.entities.PostEntity;
import com.example.postbookchallenge.domain.usecase.comments.CommentsDescription;
import com.example.postbookchallenge.presentation.base.IBaseView;

import androidx.annotation.NonNull;

interface ICommentsView
        extends IBaseView {

    /**
     * Mark post as favourite
     *
     * @param isFavourite if true the marker is highlighted
     */
    void setIsFavourite(boolean isFavourite);

    /**
     * Set the complete content of the comments activity
     *
     * @param commentsDescription all comments and the post
     */
    void setComments(@NonNull CommentsDescription commentsDescription);

    /**
     * Set the post holding the header information
     *
     * @param postEntity the whole post
     */
    void setPost(@NonNull PostEntity postEntity);
}
