package com.example.postbookchallenge.presentation.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

@SuppressWarnings({"WeakerAccess", "unused"})
abstract public class BasePresenter<T extends IBaseView>
        implements IBasePresenter {

    protected final T view;

    private CompositeDisposable compositeDisposable;

    @SuppressWarnings("unused")
    protected BasePresenter(@NonNull T view) {
        this.view = view;
    }

    @CallSuper
    @Override
    public void onNewIntent(@NonNull Intent intent) {
    }

    /**
     * Overwrite this to restore instance state data
     *
     * @param savedInstanceState the Bundle holding the instanceState
     */
    @CallSuper
    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
    }

    /**
     * Overwrite this for activity results
     *
     * @param requestCode the code provided on startWithResult()
     * @param resultCode  the code provided from setResult() of the started activity
     * @param intent      the intent holding the result data
     */
    @CallSuper
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    }

    /**
     * Overwrite this for all presenter actions onResume
     */
    @CallSuper
    @Override
    public void onResume() {
    }

    /**
     * Overwrite this for all presenter actions onPause
     */
    @CallSuper
    @Override
    public void onPause() {
        disposeDisposable();
    }

    /**
     * Overwrite this to save instance state data
     *
     * @param savedInstanceState the Bundle to store the instanceState in
     */
    @CallSuper
    @Override
    public void onSaveInstanceState(@Nullable Bundle savedInstanceState) {
    }

    /**
     * Convenience method to provide the application context
     *
     * @return the application context
     */
    @SuppressWarnings("unused")
    protected Context getApplicationContext() {
        return ((Context) view).getApplicationContext();
    }

    /**
     * Add a disposable to the composite disposable of this presenter
     * <p>
     * The composite disposable will automatically be disposed {@link #onPause()}
     *
     * @param disposable the disposable to add to the composite disposable
     */
    protected void addDisposable(@NonNull Disposable disposable) {
        if(compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    /**
     * Dispose all disposable contained in the composite disposable
     */
    protected void disposeDisposable() {
        if(compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }
}
