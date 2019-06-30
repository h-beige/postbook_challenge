package com.example.postbookchallenge.infrastructure.rxjava;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MySchedulers {

    public Scheduler io() {
        return Schedulers.io();
    }

    public Scheduler mainThread() {
        return AndroidSchedulers.mainThread();
    }
}
