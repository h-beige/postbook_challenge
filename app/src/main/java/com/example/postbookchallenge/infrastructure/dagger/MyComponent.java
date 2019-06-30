package com.example.postbookchallenge.infrastructure.dagger;

import com.example.postbookchallenge.presentation.comments.CommentsPresenter;
import com.example.postbookchallenge.presentation.login.LoginPresenter;
import com.example.postbookchallenge.presentation.posts.PostsPresenter;
import com.example.postbookchallenge.presentation.posts.list.PostsAdapter;

import javax.inject.Singleton;

@Singleton
@dagger.Component(modules = MyModule.class)
public interface MyComponent {
    void inject(CommentsPresenter instance);
    void inject(LoginPresenter instance);
    void inject(PostsAdapter postsinstance);
    void inject(PostsPresenter instance);
}
