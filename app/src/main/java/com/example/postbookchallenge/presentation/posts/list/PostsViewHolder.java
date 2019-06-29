package com.example.postbookchallenge.presentation.posts.list;


import android.view.View;
import android.widget.TextView;

import com.example.postbookchallenge.R;
import com.example.postbookchallenge.domain.entities.PostEntity;
import com.example.postbookchallenge.domain.favourites.Favourites;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

class PostsViewHolder
        extends RecyclerView.ViewHolder {

    private final PostsAdapter postsAdapter;

    private TextView tvTitle;
    private TextView tvBody;
    private TextView tvFavourite;

    PostsViewHolder(@NonNull View itemView, @NonNull PostsAdapter postsAdapter) {
        super(itemView);
        this.postsAdapter = postsAdapter;

        tvTitle = itemView.findViewById(R.id.post_tvTitle);
        tvBody = itemView.findViewById(R.id.post_tvBody);
        tvFavourite = itemView.findViewById(R.id.post_tvFavourite);
    }

    /**
     * Set post content
     *
     * @param postEntity all information about this particular post
     * @param favourites the object to access the information about the favourites
     */
    void setPost(
            @NonNull final PostEntity postEntity,
            @NonNull final Favourites favourites) {

        tvTitle.setText(postEntity.getTitle());
        tvBody.setText(postEntity.getBody());

        final boolean isFavourite = favourites.isFavourite(postEntity);
        @ColorInt
        int color = ContextCompat.getColor(
                itemView.getContext(),
                isFavourite
                ? R.color.filterChecked
                : R.color.filterUnChecked);
        tvFavourite.setBackgroundColor(color);
        tvFavourite.setOnClickListener((view) -> {
            favourites.setFavourite(postEntity, !isFavourite);
            postsAdapter.notifyDataSetChanged();
        });
    }
}
