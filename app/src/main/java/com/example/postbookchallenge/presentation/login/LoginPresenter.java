package com.example.postbookchallenge.presentation.login;

import com.example.postbookchallenge.PostBookChallengeApplication;
import com.example.postbookchallenge.domain.usecase.login.LoginUseCase;
import com.example.postbookchallenge.infrastructure.rxjava.MySchedulers;
import com.example.postbookchallenge.presentation.base.BasePresenter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;

public class LoginPresenter
        extends BasePresenter<ILoginView>
        implements ILoginPresenter {

    private static final Logger logger = LoggerFactory.getLogger(LoginPresenter.class.getSimpleName());

    @Inject LoginUseCase loginUseCase;
    @Inject MySchedulers mySchedulers;

    LoginPresenter(@NonNull ILoginView view) {
        super(view);

        PostBookChallengeApplication.getMyComponent().inject(this);
    }

    @Override
    public void onClickLogin(@NonNull String userId) {
        if(StringUtils.isBlank(userId)) {
            view.feedbackUserIdIsEmpty();
            return;
        }

        view.startLoading();
        Disposable disposable = loginUseCase
                .checkUserId(userId)
                .subscribeOn(mySchedulers.io())
                .observeOn(mySchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Boolean>(){
                    @Override
                    public void onSuccess(Boolean isValidUserId) {
                        view.stopLoading();
                        if(isValidUserId) {
                            view.navigateToPosts(Integer.parseInt(userId));
                        }
                        else {
                            view.feedbackInvalidUserId();
                        }
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
