package com.example.postbookchallenge.presentation.posts;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.postbookchallenge.R;
import com.example.postbookchallenge.common.IntentFactory;
import com.example.postbookchallenge.domain.usecase.posts.PostsDescription;
import com.example.postbookchallenge.presentation.base.BaseView;
import com.example.postbookchallenge.presentation.posts.list.PostsAdapter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.ColorInt;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class PostsView
        extends BaseView<IPostsPresenter>
        implements IPostsView,
                   View.OnClickListener,
                   IClickOnPostListener {

    public static final int FILTER_ALL = 0;
    public static final int FILTER_FAVOURITES = 1;

    @IntDef({FILTER_ALL, FILTER_FAVOURITES})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FilterState {
    }

    @BindView(R.id.posts_tvFilterAll) TextView tvFilterAll;
    @BindView(R.id.posts_tvFilterFavourites) TextView tvFilterFavourites;
    @BindView(R.id.posts_tvNoPosts) TextView tvNoPosts;
    @BindView(R.id.posts_rvPosts) RecyclerView rvPosts;

    private PostsAdapter postsAdapter;

    @Override
    public IPostsPresenter createPresenter() {
        Intent intent = getIntent();
        int userId = intent.getIntExtra(IntentFactory.INTENT_EXTRA_USER_ID, -1);
        return new PostsPresenter(this, userId);
    }

    @Override
    public int getContentViewResourceId() {
        return R.layout.activity_posts;
    }

    @Override
    public void setupView() {
        tvFilterAll.setOnClickListener(this);
        tvFilterFavourites.setOnClickListener(this);
        tvNoPosts.setVisibility(View.GONE);
        rvPosts.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.posts_tvFilterAll:
                presenter.filter(FILTER_ALL);
                break;
            case R.id.posts_tvFilterFavourites:
                presenter.filter(FILTER_FAVOURITES);
                break;
        }
    }

    @Override
    public void setContent(@NonNull PostsDescription postsDescription) {
        showFilterState(postsDescription.getFilterState());

        if(!postsDescription.hasPosts()) {
            rvPosts.setVisibility(View.GONE);
            tvNoPosts.setVisibility(View.VISIBLE);
        }
        else {
            rvPosts.setVisibility(View.VISIBLE);
            tvNoPosts.setVisibility(View.GONE);
        }


        if(postsAdapter == null) {
            postsAdapter = new PostsAdapter(this);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            rvPosts.setLayoutManager(layoutManager);
            rvPosts.setAdapter(postsAdapter);
        }

        postsAdapter.setPosts(postsDescription.getPosts());
    }

    public void showFilterState(@FilterState int filterState) {
        @ColorInt int checked = ContextCompat.getColor(this, R.color.filterChecked);
        @ColorInt int unchecked = ContextCompat.getColor(this, R.color.filterUnChecked);
        tvFilterAll.setBackgroundColor(filterState == FILTER_ALL ? checked : unchecked);
        tvFilterFavourites.setBackgroundColor(filterState == FILTER_FAVOURITES
                                              ? checked
                                              : unchecked);
    }

    @Override
    public void onPostClick(int userId, int postId) {
        startActivity(IntentFactory.createCommentsIntent(this, userId, postId));
    }
}
