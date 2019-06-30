package com.example.postbookchallenge;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.postbookchallenge.infrastructure.dagger.DaggerMyComponent;
import com.example.postbookchallenge.infrastructure.dagger.MyComponent;

import androidx.annotation.Nullable;

public class PostBookChallengeApplication
        extends Application {


    @SuppressLint("StaticFieldLeak")
    private static Context appContext;

    private static MyComponent myComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }

    public static MyComponent getMyComponent() {
        if(myComponent == null) {
            myComponent = DaggerMyComponent.builder().build();
        }
        return myComponent;
    }

    public static void setComponent(@Nullable MyComponent newMyComponent) {
        myComponent = newMyComponent;
    }

    /**
     * @return the application context
     */
    public static Context getAppContext() {
        return appContext;
    }

    /**
     * @return the shared preferences used throughout this app
     */
    public static SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(appContext);
    }
}
