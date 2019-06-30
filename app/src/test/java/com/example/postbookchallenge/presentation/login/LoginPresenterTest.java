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
import static org.mockito.Mockito.never;
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

        //ensure direct execution of rxJava execution chain - no deferred execution
        doReturn(Schedulers.trampoline()).when(mockMySchedulers).io();
        doReturn(Schedulers.trampoline()).when(mockMySchedulers).mainThread();

        //control mock module provisioning
        doReturn(mockMySchedulers).when(mockMyModule).provideMySchedulers();
        doReturn(mockLoginUseCase).when(mockMyModule).provideLoginUseCase(any(TypicodeApi.class));

        //inject mocked module into dagger graph
        PostBookChallengeApplication
                .setComponent(DaggerMyComponent.builder()
                                               .myModule(mockMyModule)
                                               .build());

        testSubject = new LoginPresenter(mockLoginView);
    }

    @After
    public void tearDown() {
        //reset manipulated dagger component
        PostBookChallengeApplication.resetComponent();
    }

    /**
     * Test the happy path. A user id has been entered, that exists. Then the navigation should start to the users posts
     */
    @Test
    public void test_clickClickLogin_happyPath() {
        String userId = "5";

        //simulate: service request succeeded and user exists
        doReturn(Single.just(true)).when(mockLoginUseCase).checkUserId(userId);

        //run actual test
        testSubject.onClickLogin(userId);

        //expected result: loading throbber has run and been stopped -> navigate to posts activity
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

        //simulate: service request succeeded and user does not exists
        doReturn(Single.just(false)).when(mockLoginUseCase).checkUserId(userId);

        //run actual test
        testSubject.onClickLogin(userId);

        //expected result: loading throbber has run and been stopped -> user feedback
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

        //simulate: exception (e.g. service io) in rxJava execution chain
        Exception exception = new Exception("Some error");
        Single<Boolean> exceptionSingle = Single
                .just(false)
                .map(aBoolean -> {
                    throw exception;
                });
        doReturn(exceptionSingle).when(mockLoginUseCase).checkUserId(userId);

        //run actual test
        testSubject.onClickLogin(userId);

        //expected result: loading throbber has run and been stopped -> user feedback
        verify(mockLoginView).startLoading();
        verify(mockLoginView).stopLoading();
        verify(mockLoginView).feedbackServiceError();
    }

    /**
     * Test an error case. The no user id has been entered. Specific feedback should be displayed
     */
    @Test
    public void test_clickClickLogin_emptyUserId() {

        //run actual test
        testSubject.onClickLogin("");

        //expected result: loading throbber has not run -> user feedback
        verify(mockLoginView, never()).startLoading();
        verify(mockLoginView).feedbackUserIdIsEmpty();
    }
}
