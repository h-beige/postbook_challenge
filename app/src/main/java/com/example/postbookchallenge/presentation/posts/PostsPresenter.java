package com.example.postbookchallenge.presentation.posts;

import android.os.Bundle;

import com.example.postbookchallenge.PostBookChallengeApplication;
import com.example.postbookchallenge.domain.favourites.Favourites;
import com.example.postbookchallenge.domain.usecase.posts.PostsDescription;
import com.example.postbookchallenge.domain.usecase.posts.PostsUseCase;
import com.example.postbookchallenge.presentation.base.BasePresenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import icepick.Icepick;
import icepick.State;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class PostsPresenter
        extends BasePresenter<IPostsView>
        implements IPostsPresenter {

    private static final Logger logger = LoggerFactory.getLogger(PostsPresenter.class.getSimpleName());

    @State @PostsView.FilterState Integer filterState;

    @Inject PostsUseCase postsUseCase;
    @Inject Favourites favourites;

    private final int userId;

    PostsPresenter(@NonNull IPostsView view, int userId) {
        super(view);
        PostBookChallengeApplication.getComponent().inject(this);
        this.userId = userId;
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle inState) {
        super.onRestoreInstanceState(inState);
        Icepick.restoreInstanceState(this, inState);
    }

    @Override
    public void onResume() {
        super.onResume();

        if(filterState == null) {
            filterState = PostsView.FILTER_ALL;
        }

        filter(filterState);
    }

    @Override
    public void onSaveInstanceState(@Nullable Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public void filter(int filterState) {

        view.startLoading();
        Disposable disposable = postsUseCase
                .loadPosts(userId, filterState, favourites.getFavouritePosts())
                .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSingleObserver<PostsDescription>() {
            @Override
            public void onSuccess(PostsDescription postsDescription) {
                view.stopLoading();
                view.setContent(postsDescription);
            }

            @Override
            public void onError(Throwable e) {
                logger.error("An error occurred while checking the user id", e);
                view.stopLoading();
                view.feedbackServiceError();
            }
        });

        addDisposable(disposable);
    }
}
