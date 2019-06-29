package com.example.postbookchallenge.common;

import android.content.Context;
import android.content.Intent;

import com.example.postbookchallenge.presentation.posts.PostsView;

import javax.inject.Singleton;

import androidx.annotation.NonNull;

@Singleton
public class IntentFactory {

    public static final String INTENT_EXTRA_USER_ID = "userId";

    /**
     * @param context needed context
     * @param userId  the userId for which the posts should be displayed
     * @return the intent to the posts activity holding the userId
     */
    public static Intent createPostsIntent(@NonNull Context context, int userId) {
        Intent intent = new Intent(context, PostsView.class);
        intent.putExtra(INTENT_EXTRA_USER_ID, userId);
        return intent;
    }
}
