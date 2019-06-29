package com.example.postbookchallenge.domain.usecase.posts;

import com.example.postbookchallenge.domain.entities.PostEntity;
import com.example.postbookchallenge.domain.service.ITypicodeService;
import com.example.postbookchallenge.domain.service.TypicodeApi;
import com.example.postbookchallenge.presentation.posts.PostsView;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import io.reactivex.Single;
import io.reactivex.functions.Function;

@Singleton
public class PostsUseCase {

    private final TypicodeApi typicodeApi;

    @Inject
    PostsUseCase(@NonNull TypicodeApi typicodeApi) {
        this.typicodeApi = typicodeApi;
    }

    /**
     * Load all posts of the passed userId and filter by favourites if requested
     *
     * @param userId      the userId to fetch the posts for
     * @param filterState one of the @PostsView.FilterState constants
     * @param favourites  the set with all posts marked as favourites
     * @return the single wrapping the description with all content
     */
    public Single<PostsDescription> loadPosts(
            int userId,
            @PostsView.FilterState int filterState,
            @NonNull final Set<String> favourites) {

        ITypicodeService typicodeService = typicodeApi.getTypicodeService();

        return typicodeService
                .getPosts(userId)
                .flatMapIterable((Function<List<PostEntity>, Iterable<PostEntity>>) postEntities -> postEntities)
                .filter(postEntity ->
                                filterState == PostsView.FILTER_ALL ||
                                        favourites.contains(postEntity.getKey()))
                .toList()
                .map(postEntities -> new PostsDescription().setPosts(postEntities)
                                                           .setFilterState(filterState));
    }
}
