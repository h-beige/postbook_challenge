package com.example.postbookchallenge.domain.service;

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
}
