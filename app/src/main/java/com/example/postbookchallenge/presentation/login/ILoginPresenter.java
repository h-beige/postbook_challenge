package com.example.postbookchallenge.presentation.login;

import com.example.postbookchallenge.presentation.base.IBasePresenter;

import androidx.annotation.NonNull;

public interface ILoginPresenter
        extends IBasePresenter {

    /**
     * Executed when the button has been click
     *
     * @param toString the content of the user id edit text
     */
    void onClickLogin(@NonNull String toString);
}
