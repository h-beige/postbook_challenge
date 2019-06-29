package com.example.postbookchallenge.presentation.posts;

import com.example.postbookchallenge.domain.usecase.posts.PostsDescription;
import com.example.postbookchallenge.presentation.base.IBaseView;

import androidx.annotation.NonNull;

public interface IPostsView
        extends IBaseView {

    /**
     * Set the content of the posts activity
     *
     * @param postsDescription description holding the complete content
     */
    void setContent(@NonNull PostsDescription postsDescription);
}
