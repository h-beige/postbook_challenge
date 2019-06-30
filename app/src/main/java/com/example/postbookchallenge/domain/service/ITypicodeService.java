package com.example.postbookchallenge.domain.service;

import com.example.postbookchallenge.domain.entities.CommentEntity;
import com.example.postbookchallenge.domain.entities.PostEntity;
import com.example.postbookchallenge.domain.entities.UserEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ITypicodeService {

    /**
     * Example of resulting URL: https://jsonplaceholder.typicode.com/users?id=6
     *
     * @param userId the id to query
     * @return Observable of the
     */
    @GET("users")
    Observable<List<UserEntity>> getUsers(@Query("id") String userId);

    /**
     * Example of resulting URL: https://jsonplaceholder.typicode.com/posts?userId=6
     *
     * @param userId the id to query
     * @return Observable of the
     */
    @GET("posts")
    Observable<List<PostEntity>> getPosts(@Query("userId") int userId);

    /**
     * Example of resulting URL: https://jsonplaceholder.typicode.com/posts?id=52
     *
     * @param postId the id to query
     * @return Observable of the
     */
    @GET("posts")
    Observable<List<PostEntity>> getPost(@Query("id") int postId);

    /**
     * Example of resulting URL: https://jsonplaceholder.typicode.com/comments?postId=1
     *
     * @param postId the id to query
     * @return Observable of the
     */
    @GET("comments")
    Observable<List<CommentEntity>> getComments(@Query("postId") int postId);
}
