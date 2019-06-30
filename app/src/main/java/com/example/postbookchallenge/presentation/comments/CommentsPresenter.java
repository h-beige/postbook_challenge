package com.example.postbookchallenge.presentation.comments;

import com.example.postbookchallenge.PostBookChallengeApplication;
import com.example.postbookchallenge.domain.entities.PostEntity;
import com.example.postbookchallenge.domain.favourites.Favourites;
import com.example.postbookchallenge.domain.usecase.comments.CommentsDescription;
import com.example.postbookchallenge.domain.usecase.comments.CommentsUseCase;
import com.example.postbookchallenge.presentation.base.BasePresenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class CommentsPresenter
        extends BasePresenter<ICommentsView>
        implements ICommentsPresenter {

    private static final Logger logger = LoggerFactory.getLogger(CommentsPresenter.class.getSimpleName());

    @Inject Favourites favourites;
    @Inject CommentsUseCase commentsUseCase;

    private final int userId;
    private final int postId;

    CommentsPresenter(@NonNull ICommentsView view, int userId, int postId) {
        super(view);
        this.userId = userId;
        this.postId = postId;

        PostBookChallengeApplication.getComponent().inject(this);
    }

    public void onResume() {
        super.onResume();

        view.setIsFavourite(favourites.isFavourite(userId, postId));
        loadComments();
    }

    private void loadComments() {

        view.startLoading();
        Disposable disposable = commentsUseCase
                .loadComments(postId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommentsDescription>() {
                    @Override
                    public void onSuccess(CommentsDescription commentsDescription) {
                        view.setComments(commentsDescription);
                        loadPost();
                    }

                    @Override
                    public void onError(Throwable e) {
                        logger.error("An error occurred while loading the comments for post id " + postId, e);
                        view.stopLoading();
                        view.feedbackServiceError();
                    }
                });
        addDisposable(disposable);
    }

    private void loadPost() {
        Disposable disposable = commentsUseCase
                .loadPost(postId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<PostEntity>() {
                    @Override
                    public void onSuccess(PostEntity postEntity) {
                        view.stopLoading();
                        view.setPost(postEntity);
                    }

                    @Override
                    public void onError(Throwable e) {
                        logger.error("An error occurred while loading the post for post id " + postId, e);
                        view.stopLoading();
                        view.feedbackServiceError();
                    }
                });
        addDisposable(disposable);
    }
}
