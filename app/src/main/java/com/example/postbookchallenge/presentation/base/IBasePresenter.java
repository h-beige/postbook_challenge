package com.example.postbookchallenge.presentation.base;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

@SuppressWarnings({"EmptyMethod", "unused"})
public interface IBasePresenter {

    /**
     * Presenter method for the onNewIntent method executed in the view in its onNewIntent method
     *
     * According to the activity lifecycle, the activity is paused and resumed when it receives a new intent.
     * This method is executed after onPause and before onResume, so be careful what you try to do.
     * Some things are not allowed after an activity is paused and before it is resumed
     *
     * @param intent the new intent
     */
    void onNewIntent(@NonNull Intent intent);

    /**
     * Presenter method to restore instance state data
     * @param savedInstanceState the Bundle holding the instanceState
     */
    void onRestoreInstanceState(@Nullable Bundle savedInstanceState);

    /**
     * Presenter method to process result from a startWithResult() Activity return
     * @param requestCode the code provided on startWithResult()
     * @param resultCode the code provided from setResult() of the started activity
     * @param intent the intent holding the result data
     */
    void onActivityResult(int requestCode, int resultCode, Intent intent);

    /**
     * Presenter method representing the presenter specific onResume() of the activity lifecycle
     */
    void onResume();

    /**
     * Presenter method representing the presenter specific onPause() of the activity lifecycle
     */
    void onPause();

    /**
     * Presenter method to store instance state data
     * @param savedInstanceState the Bundle to store the instanceState in
     */
    void onSaveInstanceState(@Nullable Bundle savedInstanceState);
}
