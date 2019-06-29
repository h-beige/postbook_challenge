package com.example.postbookchallenge.presentation.login;

import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.postbookchallenge.R;
import com.example.postbookchallenge.common.IntentFactory;
import com.example.postbookchallenge.common.UiHelper;
import com.example.postbookchallenge.presentation.base.BaseView;

import androidx.annotation.NonNull;
import butterknife.BindView;

public class LoginView
        extends BaseView<ILoginPresenter>
        implements ILoginView,
                   View.OnClickListener {

    @BindView(R.id.login_etUserId) EditText etUserId;
    @BindView(R.id.login_buttonLogin) Button buttonLogin;

    @Override
    public ILoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public int getContentViewResourceId() {
        return R.layout.activity_login;
    }

    @Override
    public void setupView() {
        etUserId.setOnEditorActionListener((view, actionId, event) -> {
                                               if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                                                   UiHelper.closeSoftKeyboard(view);
                                                   return true;
                                               }
                                               return false;
                                           }
        );
        buttonLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(@NonNull View view) {
        if(view.getId() == R.id.login_buttonLogin) {
            presenter.onClickLogin(etUserId.getText().toString());
            UiHelper.closeSoftKeyboard(view);
        }
    }

    @Override
    public void feedbackUserIdIsEmpty() {
        Toast.makeText(this, getString(R.string.login_feedback_user_id_empty), Toast.LENGTH_SHORT)
             .show();
    }

    @Override
    public void feedbackInvalidUserId() {
        Toast.makeText(this, getString(R.string.login_feedback_user_id_invalid), Toast.LENGTH_SHORT)
             .show();
    }

    @Override
    public void navigateToPosts(int userId) {
        startActivity(IntentFactory.createPostsIntent(this, userId));
    }

}
