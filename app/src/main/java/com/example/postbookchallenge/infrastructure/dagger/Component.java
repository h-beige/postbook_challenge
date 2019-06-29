package com.example.postbookchallenge.infrastructure.dagger;

import com.example.postbookchallenge.presentation.login.LoginPresenter;

import javax.inject.Singleton;

@Singleton
@dagger.Component(modules = Module.class)
public interface Component {
    void inject(LoginPresenter instance);
}
