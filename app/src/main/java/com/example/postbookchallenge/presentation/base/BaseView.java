package com.example.postbookchallenge.presentation.base;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

abstract public class BaseView<T extends IBasePresenter>
        extends AppCompatActivity
        implements IBaseView {

    private static final Logger logger = LoggerFactory.getLogger(BaseView.class.getSimpleName());

    protected T presenter;

    private boolean isActive;

    private Dialog dialog;

    @Override
    abstract public T createPresenter();

    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle inState) {
        logMethod("onCreate(<inState>)");
        super.onCreate(inState);

        setContentView(getContentViewResourceId());
        ButterKnife.bind(this);

        postSetContentView();

        setupView();

        presenter = createPresenter();
    }

    /**
     * Overwrite this to implement View bindings (e.g. Butterknife)
     */
    protected void postSetContentView() {}

    @CallSuper
    @Override
    protected void onPostCreate(Bundle inState) {
        logMethod("onPostCreate(<inState>)");
        super.onPostCreate(inState);
    }

    @CallSuper
    @Override
    protected void onRestoreInstanceState(@Nullable Bundle inState) {
        logMethod("onRestoreInstanceState(<inState>)",
                "Bundle is " + (inState == null ? "not set" : "set")
        );
        super.onRestoreInstanceState(inState);
        presenter.onRestoreInstanceState(inState);
    }

    @CallSuper
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        logMethod("onActivityResult(<requestCode>, <resultCode>, <intent>)");
        super.onActivityResult(requestCode, resultCode, intent);
        presenter.onActivityResult(requestCode, resultCode, intent);
    }

    @CallSuper
    @Override
    public void onNewIntent(@NonNull Intent intent) {
        logMethod("onNewIntent(<intent>)");
        presenter.onNewIntent(intent);
    }

    @CallSuper
    @Override
    protected void onResume() {
        logMethod("onResume()");
        super.onResume();
        isActive = true;
        presenter.onResume();
    }

    @CallSuper
    @Override
    public void onConfigurationChanged(Configuration configuration) {
        logMethod("onConfigurationChanged(<configuration>)");
        super.onConfigurationChanged(configuration);
    }

    @CallSuper
    @Override
    protected void onPause() {
        logMethod("onPause()");
        isActive = false;
        presenter.onPause();

        cleanupDialog();

        super.onPause();
    }

    @CallSuper
    @Override
    protected void onSaveInstanceState(@Nullable Bundle outState) {
        logMethod("onSaveInstanceState(<outState>)");
        presenter.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @CallSuper
    @Override
    protected void onDestroy() {
        logMethod("onDestroy()");
        super.onDestroy();
    }

    @CallSuper
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        logMethod("onOptionsMenuSelected(<menuItem>)", " item title: " + menuItem.getTitle());
        return super.onOptionsItemSelected(menuItem);
    }

    /**
     * Set the current dialog - use this method so the BaseView can take care of cleanups in onPause()
     *
     * @param dialog the dialog to be maintained - if null the dialog is simply reset
     *               the use of the {@see #cleanupDialog} is encouraged though
     */
    @SuppressWarnings("unused")
    protected void setDialog(@Nullable Dialog dialog) {
        cleanupDialog();
        this.dialog = dialog;
    }

    /**
     * Safely cleanup the alterDialog and dismiss and release resources and the link to this Context
     */
    private void cleanupDialog() {
        if(dialog != null) {
            if(dialog.isShowing()) {
                dialog.dismiss();
            }
            dialog = null;
        }
    }

    @CallSuper
    @Override
    public void finishWithResult(int resultCode) {
        logMethod("finishWithResult(<resultCode>)");
        setResult(resultCode);
        finish();
    }

    @CallSuper
    @Override
    public void finishWithResult(int resultCode, @NonNull Intent activityResult) {
        logMethod("finishWithResult(<resultCode>, <activityResult>)");
        setResult(resultCode, activityResult);
        finish();
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    /**
     * Simple logger method wrapping the a debug state condition check
     *
     * @param methodName the method name to log
     */
    private void logMethod(@NonNull String methodName) {
        if(logger.isDebugEnabled()) {
            logger.debug(methodName + ": " + getClass().getSimpleName());
        }
    }

    /**
     * Simple logger method wrapping the a debug state condition check
     *
     * @param methodName     the method name to log
     * @param additionalInfo anything can be added here in addition to regular method logging
     */
    private void logMethod(@NonNull String methodName, @NonNull String additionalInfo) {
        if(logger.isDebugEnabled()) {
            logger.debug(methodName + ": " + getClass().getSimpleName() + " - " + additionalInfo);
        }
    }
}
