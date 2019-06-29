package com.example.postbookchallenge.presentation.login;

import com.example.postbookchallenge.PostBookChallengeApplication;
import com.example.postbookchallenge.domain.usecase.login.LoginUseCase;
import com.example.postbookchallenge.presentation.base.BasePresenter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter
        extends BasePresenter<ILoginView>
        implements ILoginPresenter {

    private static final Logger logger = LoggerFactory.getLogger(LoginPresenter.class.getSimpleName());

    @Inject
    LoginUseCase loginUseCase;

    LoginPresenter(@NonNull ILoginView view) {
        super(view);

        PostBookChallengeApplication.getComponent().inject(this);
    }

    @Override
    public void onClickLogin(@NonNull String userId) {
        if(StringUtils.isBlank(userId)) {
            view.feedbackUserIdIsEmpty();
            return;
        }

        Disposable disposable = loginUseCase
                .checkUserId(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Boolean>(){
                    @Override
                    public void onSuccess(Boolean isValidUserId) {
                        if(isValidUserId) {
                            view.navigateToPosts(userId);
                        }
                        else {
                            view.feedbackInvalidUserId();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        logger.error("An error occurred while checking the user id", e);
                        view.feedbackServiceError();
                    }
                });
        addDisposable(disposable);
    }
}
