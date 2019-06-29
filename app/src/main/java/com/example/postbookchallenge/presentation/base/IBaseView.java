package com.example.postbookchallenge.presentation.base;

import android.content.Intent;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

@SuppressWarnings({"unused"})
public interface IBaseView {

    /**
     * Provide the presenter related to this View
     *
     * @return presenter instance at least of type IBasePresenter
     */
    IBasePresenter createPresenter();

    /**
     * Provide the layout resource id of the current views content
     *
     * @return layout resource id of the content layout
     */
    @LayoutRes
    int getContentViewResourceId();

    /**
     * Place to setup the view with initial values, listeners, ... basic UI settings
     * <p>
     * This method will be executed AFTER the onCreate method implementation
     */
    void setupView();

    /**
     * Finish the current activity
     */
    @CallSuper
    void finish();

    /**
     * Finish the current activity and set just a result code
     *
     * @param resultCode an integer forwarded to the calling activity as result code
     */
    void finishWithResult(int resultCode);

    /**
     * Finish the current activity and set a result
     * <p>
     * The result is passed to the activity that started this activity with startActivityForResult
     *
     * @param resultCode     an integer forwarded to the calling activity as result code
     * @param activityResult the activities result
     */
    void finishWithResult(int resultCode, @NonNull Intent activityResult);

    /**
     * Change visibility of onBackPressed to public, so the presenter may access it
     */
    void onBackPressed();

    /**
     * Provides the status whether the current activity is active, means is in between onResume and onPause
     *
     * @return true if active
     */
    boolean isActive();
}