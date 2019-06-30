package com.example.postbookchallenge.presentation.comments;

import android.content.Intent;
import android.widget.TextView;

import com.example.postbookchallenge.R;
import com.example.postbookchallenge.common.IntentFactory;
import com.example.postbookchallenge.domain.entities.PostEntity;
import com.example.postbookchallenge.domain.usecase.comments.CommentsDescription;
import com.example.postbookchallenge.presentation.base.BaseView;
import com.example.postbookchallenge.presentation.comments.list.CommentsAdapter;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class CommentsView
        extends BaseView<ICommentsPresenter>
        implements ICommentsView {

    @BindView(R.id.post_tvFavourite) TextView tvFavourite;
    @BindView(R.id.post_tvTitle) TextView tvTitle;
    @BindView(R.id.post_tvBody) TextView tvBody;

    @BindView(R.id.comments_rvComments) RecyclerView rvComments;


    private CommentsAdapter commentsAdapter;

    @Override
    public ICommentsPresenter createPresenter() {
        Intent intent = getIntent();
        int userId = intent.getIntExtra(IntentFactory.INTENT_EXTRA_USER_ID, -1);
        int postId = intent.getIntExtra(IntentFactory.INTENT_EXTRA_POST_ID, -1);

        return new CommentsPresenter(this, userId, postId);
    }

    @Override
    public int getContentViewResourceId() {
        return R.layout.activity_comments;
    }

    @Override
    public void setupView() {

    }

    @Override
    public void setIsFavourite(boolean isFavourite) {
        @ColorInt
        int color = ContextCompat.getColor(
                this,
                isFavourite
                ? R.color.filterChecked
                : R.color.filterUnChecked);
        tvFavourite.setBackgroundColor(color);
    }

    @Override
    public void setComments(@NonNull CommentsDescription commentsDescription) {

        if(commentsAdapter == null) {
            commentsAdapter = new CommentsAdapter();
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            rvComments.setLayoutManager(layoutManager);
            rvComments.setAdapter(commentsAdapter);
        }

        commentsAdapter.setComments(commentsDescription.getComments());
    }

    @Override
    public void setPost(@NonNull PostEntity postEntity) {
        tvTitle.setText(postEntity.getTitle());
        tvBody.setText(postEntity.getBody());
    }
}
