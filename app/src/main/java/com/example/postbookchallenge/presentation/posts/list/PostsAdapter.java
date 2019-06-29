package com.example.postbookchallenge.presentation.posts.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.postbookchallenge.PostBookChallengeApplication;
import com.example.postbookchallenge.R;
import com.example.postbookchallenge.domain.entities.PostEntity;
import com.example.postbookchallenge.domain.favourites.Favourites;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostsAdapter
        extends RecyclerView.Adapter<PostsViewHolder> {

    private List<PostEntity> posts;

    @Inject
    Favourites favourites;

    public PostsAdapter() {
        PostBookChallengeApplication.getComponent().inject(this);
    }

    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new PostsViewHolder(inflater.inflate(R.layout.view_post, parent, false), this);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsViewHolder holder, int position) {
        PostEntity postEntity = posts.get(position);
        holder.setPost(postEntity, favourites);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setPosts(@NonNull List<PostEntity> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }
}
