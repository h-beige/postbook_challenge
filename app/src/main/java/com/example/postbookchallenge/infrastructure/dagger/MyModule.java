package com.example.postbookchallenge.infrastructure.dagger;

import com.example.postbookchallenge.domain.service.TypicodeApi;
import com.example.postbookchallenge.domain.usecase.login.LoginUseCase;
import com.example.postbookchallenge.infrastructure.rxjava.MySchedulers;

import javax.inject.Singleton;

import dagger.Provides;

@dagger.Module
public class MyModule {

    @Singleton
    @Provides
    public LoginUseCase provideLoginUseCase(TypicodeApi typicodeApi) {
        return new LoginUseCase(typicodeApi);
    }

    @Singleton
    @Provides
    public MySchedulers provideMySchedulers() {
        return new MySchedulers();
    }
}
