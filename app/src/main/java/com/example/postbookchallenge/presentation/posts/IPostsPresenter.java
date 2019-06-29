package com.example.postbookchallenge.presentation.posts;

import com.example.postbookchallenge.presentation.base.IBasePresenter;

public interface IPostsPresenter
        extends IBasePresenter {
    /**
     * Change the filter
     *
     * @param filterState the kind of filtering
     */
    void filter(@PostsView.FilterState int filterState);
}
