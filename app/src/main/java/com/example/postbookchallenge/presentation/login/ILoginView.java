package com.example.postbookchallenge.presentation.login;

import com.example.postbookchallenge.presentation.base.IBaseView;

public interface ILoginView
        extends IBaseView {
    /**
     * Show user feedback that the user id field was empty
     */
    void feedbackUserIdIsEmpty();

    /**
     * Show user feedback that the entered user id is invalid / does not exist
     */
    void feedbackInvalidUserId();

    /**
     * Navigate to the posts activity
     *
     * @param userId the entered userId
     */
    void navigateToPosts(int userId);
}
