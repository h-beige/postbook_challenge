package com.example.postbookchallenge.common;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.postbookchallenge.PostBookChallengeApplication;

import androidx.annotation.NonNull;

public class UiHelper {

    /**
     * Hide the keyboard
     *
     * @param view view to grab a window token from
     */
    public static void closeSoftKeyboard(@NonNull View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) PostBookChallengeApplication
                .getAppContext()
                .getSystemService(Activity.INPUT_METHOD_SERVICE);

        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
