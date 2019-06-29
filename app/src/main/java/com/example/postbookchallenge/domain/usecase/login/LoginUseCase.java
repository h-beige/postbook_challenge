package com.example.postbookchallenge.domain.usecase.login;

import com.example.postbookchallenge.domain.service.ITypicodeService;
import com.example.postbookchallenge.domain.service.TypicodeApi;
import com.example.postbookchallenge.domain.service.UserEntity;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.reactivex.Single;
import io.reactivex.functions.Function;

public class LoginUseCase {

    private final TypicodeApi typicodeApi;

    @Inject
    public LoginUseCase(@NonNull TypicodeApi typicodeApi) {
        this.typicodeApi = typicodeApi;
    }

    /**
     * Check whether there is exactly 1 user with the passed userId via the typicode service
     *
     * @param userId the userId to check
     * @return Single wrapping the result whether the userId is valid
     */
    public Single<Boolean> checkUserId(@NonNull String userId) {
        ITypicodeService typicodeService = typicodeApi.getTypicodeService();

        return typicodeService
                .getUsers(userId)
                .flatMapIterable((Function<List<UserEntity>, Iterable<?>>) userEntities -> userEntities)
                .count()
                .map(amount -> amount == 1);
    }
}
