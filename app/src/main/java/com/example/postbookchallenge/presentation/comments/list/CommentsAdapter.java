package com.example.postbookchallenge.presentation.comments.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.postbookchallenge.R;
import com.example.postbookchallenge.domain.entities.CommentEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommentsAdapter
        extends RecyclerView.Adapter<CommentsViewHolder> {

    private List<CommentEntity> comments;

    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new CommentsViewHolder(inflater.inflate(R.layout.view_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {
        CommentEntity commentEntity = comments.get(position);
        holder.setContent(commentEntity);
    }

    @Override
    public int getItemCount() {
        return comments == null ? 0 : comments.size();
    }

    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }
}
