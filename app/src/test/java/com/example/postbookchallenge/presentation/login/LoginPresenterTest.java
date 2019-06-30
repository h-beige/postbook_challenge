package com.example.postbookchallenge.presentation.login;

import com.example.postbookchallenge.PostBookChallengeApplication;
import com.example.postbookchallenge.domain.service.TypicodeApi;
import com.example.postbookchallenge.domain.usecase.login.LoginUseCase;
import com.example.postbookchallenge.infrastructure.dagger.DaggerMyComponent;
import com.example.postbookchallenge.infrastructure.dagger.MyModule;
import com.example.postbookchallenge.infrastructure.rxjava.MySchedulers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
public class LoginPresenterTest {

    @Mock LoginUseCase mockLoginUseCase;
    @Mock LoginView mockLoginView;
    @Mock MyModule mockMyModule;
    @Mock MySchedulers mockMySchedulers;

    private LoginPresenter testSubject;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        doReturn(Schedulers.trampoline()).when(mockMySchedulers).io();
        doReturn(Schedulers.trampoline()).when(mockMySchedulers).mainThread();
        doReturn(mockMySchedulers).when(mockMyModule).provideMySchedulers();

        doReturn(mockLoginUseCase).when(mockMyModule).provideLoginUseCase(any(TypicodeApi.class));

        PostBookChallengeApplication
                .setComponent(DaggerMyComponent.builder()
                                               .myModule(mockMyModule)
                                               .build());

        testSubject = new LoginPresenter(mockLoginView);
    }

    @After
    public void tearDown() {
        PostBookChallengeApplication.setComponent(null);
    }

    /**
     * Test the happy path. A user id has been entered, that exists. Then the navigation should start to the users posts
     */
    @Test
    public void test_clickClickLogin_happyPath() {
        String userId = "5";
        doReturn(Single.just(true)).when(mockLoginUseCase).checkUserId(userId);

        testSubject.onClickLogin(userId);

        verify(mockLoginView).startLoading();
        verify(mockLoginView).stopLoading();
        verify(mockLoginView).navigateToPosts(Integer.parseInt(userId));
    }

    /**
     * Test an error case. The entered user id does not exist. Specific feedback should be displayed
     */
    @Test
    public void test_clickClickLogin_invalidUserId() {
        String userId = "5";
        doReturn(Single.just(false)).when(mockLoginUseCase).checkUserId(userId);

        testSubject.onClickLogin(userId);

        verify(mockLoginView).startLoading();
        verify(mockLoginView).stopLoading();
        verify(mockLoginView).feedbackInvalidUserId();
    }

    /**
     * Test an error case. The service request throws an exception. Specific feedback should be displayed
     */
    @Test
    public void test_clickClickLogin_errorWhileServiceExecution() {
        String userId = "5";
        Exception exception = new Exception("Some error");
        Single<Boolean> exceptionSingle = Single.just(false).map(aBoolean -> {
            throw exception;
        });
        doReturn(exceptionSingle).when(mockLoginUseCase).checkUserId(userId);

        testSubject.onClickLogin(userId);

        verify(mockLoginView).startLoading();
        verify(mockLoginView).stopLoading();
        verify(mockLoginView).feedbackServiceError();
    }

    /**
     * Test an error case. The no user id has been entered. Specific feedback should be displayed
     */
    @Test
    public void test_clickClickLogin_emptyUserId() {
        testSubject.onClickLogin("");

        verify(mockLoginView).feedbackUserIdIsEmpty();
    }
}
