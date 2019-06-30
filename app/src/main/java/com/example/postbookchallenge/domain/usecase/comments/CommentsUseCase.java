package com.example.postbookchallenge.domain.usecase.comments;

import com.example.postbookchallenge.domain.entities.PostEntity;
import com.example.postbookchallenge.domain.service.ITypicodeService;
import com.example.postbookchallenge.domain.service.TypicodeApi;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.reactivex.Single;
import io.reactivex.functions.Function;

public class CommentsUseCase {

    private final TypicodeApi typicodeApi;

    @Inject
    CommentsUseCase(@NonNull TypicodeApi typicodeApi) {
        this.typicodeApi = typicodeApi;
    }

    /**
     * Load all comments of the passed postId
     *
     * @param postId the postId to fetch the comments for
     * @return the single wrapping the description with all comments
     */
    public Single<CommentsDescription> loadComments(int postId) {

        ITypicodeService typicodeService = typicodeApi.getTypicodeService();

        return typicodeService
                .getComments(postId)
                .map(commentEntities -> new CommentsDescription().setComments(commentEntities))
                .first(new CommentsDescription());
    }

    /**
     * Load a single post
     *
     * @param postId the postId of the post
     * @return the single wrapping the post
     */
    public Single<PostEntity> loadPost(int postId) {

        ITypicodeService typicodeService = typicodeApi.getTypicodeService();

        return typicodeService
                .getPost(postId)
                .flatMapIterable((Function<List<PostEntity>, Iterable<PostEntity>>) postEntities -> postEntities)
                .firstOrError();
    }


}
