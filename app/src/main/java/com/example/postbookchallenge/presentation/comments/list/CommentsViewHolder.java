package com.example.postbookchallenge.presentation.comments.list;

import android.view.View;
import android.widget.TextView;

import com.example.postbookchallenge.R;
import com.example.postbookchallenge.domain.entities.CommentEntity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommentsViewHolder
        extends RecyclerView.ViewHolder {

    private final TextView tvTitle;
    private final TextView tvBody;

    CommentsViewHolder(@NonNull View itemView) {
        super(itemView);

        tvTitle = itemView.findViewById(R.id.comment_tvTitle);
        tvBody = itemView.findViewById(R.id.comment_tvBody);
    }

    public void setContent(@NonNull CommentEntity commentEntity) {
        tvTitle.setText(commentEntity.getName());
        tvBody.setText(commentEntity.getBody());
    }
}
