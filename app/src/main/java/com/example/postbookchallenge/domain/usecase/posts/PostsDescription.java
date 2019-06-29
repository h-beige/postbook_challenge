package com.example.postbookchallenge.domain.usecase.posts;

import com.example.postbookchallenge.domain.entities.PostEntity;
import com.example.postbookchallenge.presentation.posts.PostsView;

import java.util.List;

public class PostsDescription {

    private List<PostEntity> posts;

    private @PostsView.FilterState int filterState;

    public List<PostEntity> getPosts() {
        return posts;
    }

    public PostsDescription setPosts(List<PostEntity> posts) {
        this.posts = posts;
        return this;
    }

    public int getFilterState() {
        return filterState;
    }

    PostsDescription setFilterState(int filterState) {
        this.filterState = filterState;
        return this;
    }

    public boolean hasPosts() {
        return posts != null && posts.size() > 0;
    }
}
