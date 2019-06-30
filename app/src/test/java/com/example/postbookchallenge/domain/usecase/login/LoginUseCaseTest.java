package com.example.postbookchallenge.domain.usecase.login;

import com.example.postbookchallenge.PostBookChallengeApplication;
import com.example.postbookchallenge.domain.entities.UserEntity;
import com.example.postbookchallenge.domain.service.ITypicodeService;
import com.example.postbookchallenge.domain.service.TypicodeApi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;

public class LoginUseCaseTest {

    @Mock ITypicodeService mockITypicodeService;
    @Mock TypicodeApi mockTypicodeApi;

    private LoginUseCase testSubject;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        doReturn(mockITypicodeService).when(mockTypicodeApi).getTypicodeService();

        testSubject = new LoginUseCase(mockTypicodeApi);
    }

    @After
    public void tearDown() {
        PostBookChallengeApplication.setComponent(null);
    }

    /**
     * Test the happy path. The service request delivered exactly one user for the passed user id. Further processing succeeded
     */
    @Test
    public void test_checkUserId_happyPath() {

        String userId = "6";
        List<UserEntity> userEntities = new ArrayList<>();
        userEntities.add(new UserEntity().setId(userId));

        doReturn(Observable.just(userEntities)).when(mockITypicodeService).getUsers(userId);

        TestObserver<Boolean> testObserver = new TestObserver<>();
        //noinspection ResultOfMethodCallIgnored
        testSubject
                .checkUserId(userId)
                .subscribeOn(Schedulers.trampoline())
                .subscribeWith(testObserver);

        testObserver.assertSubscribed();
        testObserver.assertNoErrors();
        testObserver.assertComplete();
        assertEquals(testObserver.values().size(), 1);
        assertTrue(testObserver.values().get(0));
    }

    /**
     * Test a negative case. The service request delivered no user for the passed user id. Further processing succeeded
     */
    @Test
    public void test_checkUserId_noUserFound() {

        String userId = "6";
        doReturn(Observable.just(new ArrayList<>())).when(mockITypicodeService).getUsers(userId);

        TestObserver<Boolean> testObserver = new TestObserver<>();
        //noinspection ResultOfMethodCallIgnored
        testSubject
                .checkUserId(userId)
                .subscribeOn(Schedulers.trampoline())
                .subscribeWith(testObserver);

        testObserver.assertSubscribed();
        testObserver.assertNoErrors();
        testObserver.assertComplete();
        assertEquals(testObserver.values().size(), 1);
        assertFalse(testObserver.values().get(0));
    }

    /**
     * Test a paranoia case that should not happen in "real" life. The service request delivered several users for one passed user id. Further processing succeeded
     */
    @Test
    public void test_checkUserId_multipleUsersFound() {

        String userId = "6";
        List<UserEntity> userEntities = new ArrayList<>();
        userEntities.add(new UserEntity().setId(userId));
        userEntities.add(new UserEntity().setId("asdf"));

        doReturn(Observable.just(userEntities)).when(mockITypicodeService).getUsers(userId);

        TestObserver<Boolean> testObserver = new TestObserver<>();
        //noinspection ResultOfMethodCallIgnored
        testSubject
                .checkUserId(userId)
                .subscribeOn(Schedulers.trampoline())
                .subscribeWith(testObserver);

        testObserver.assertSubscribed();
        testObserver.assertNoErrors();
        testObserver.assertComplete();
        assertEquals(testObserver.values().size(), 1);
        assertFalse(testObserver.values().get(0));
    }

    /**
     * Test an error case. The service request threw an exception
     */
    @Test
    public void test_checkUserId_exceptionWhileLoading() {

        String userId = "6";
        List<UserEntity> userEntities = new ArrayList<>();
        userEntities.add(new UserEntity().setId(userId));

        Exception expectedException = new Exception("Test exception");
        Observable<List<UserEntity>> observable = Observable
                .just(userEntities)
                .map(userEntities1 -> {
                    throw expectedException;
                });
        doReturn(observable).when(mockITypicodeService).getUsers(userId);

        TestObserver<Boolean> testObserver = new TestObserver<>();
        //noinspection ResultOfMethodCallIgnored
        testSubject
                .checkUserId(userId)
                .subscribeOn(Schedulers.trampoline())
                .subscribeWith(testObserver);

        testObserver.assertSubscribed();
        testObserver.assertError(expectedException);
        testObserver.assertNotComplete();
    }
}
