package com.example.postbookchallenge;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.postbookchallenge.infrastructure.dagger.Component;
import com.example.postbookchallenge.infrastructure.dagger.DaggerComponent;

public class PostBookChallengeApplication
        extends Application {


    @SuppressLint("StaticFieldLeak")
    private static Context appContext;

    private static Component component;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        component = DaggerComponent.builder().build();
    }

    public static Component getComponent() {
        return component;
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
