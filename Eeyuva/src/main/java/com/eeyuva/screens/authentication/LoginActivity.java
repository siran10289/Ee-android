package com.eeyuva.screens.authentication;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eeyuva.di.component.DaggerLoginComponent;
import com.eeyuva.screens.home.HomeActivity;
import com.eeyuva.di.module.LoginPresenterModule;
import com.eeyuva.ButterAppCompatActivity;
import com.eeyuva.R;
import com.eeyuva.di.component.LoginComponent;
import com.eeyuva.screens.registration.RegistrationActivity;
import com.eeyuva.utils.Utils;
import com.eeyuva.utils.navigation.NavigationUtils;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by hari on 22/6/16.
 */
public class LoginActivity extends ButterAppCompatActivity implements LoginContract.View {

    @Inject
    LoginContract.Presenter mPresenter;

    private LoginComponent mComponent;

    @Bind(R.id.mEdtUsername)
    EditText mEdtUsername;

    @Bind(R.id.mEdtPassword)
    EditText mEdtPassword;

    @Bind(R.id.mBtnLogin)
    Button mLogin;

    @Bind(R.id.mTxtForgot)
    TextView mTxtForgot;

    AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponent();
        mPresenter.setView(this);
        mEdtPassword.setOnEditorActionListener(actionListener);
    }

    private void initComponent() {
        mComponent = DaggerLoginComponent.builder()
                .appComponent(getApplicationComponent())
                .loginPresenterModule(new LoginPresenterModule(this))
                .build();
        mComponent.inject(this);
    }

    @OnClick(R.id.mBtnLogin)
    public void onLoginClick() {
        Utils.hideSoftKeyBoard(LoginActivity.this);
        mPresenter.onLoginClicked();
    }

    @OnClick(R.id.mTxtSignup)
    public void OnSignUpClick() {
        mPresenter.onSignupClick();
    }

    @OnClick(R.id.mTxtForgot)
    public void OnForgotClick() {
        showShareDialog();
    }


    @Override
    public String getUsername() {
        return mEdtUsername.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return mEdtPassword.getText().toString().trim();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void movetoSignUp() {
        NavigationUtils.startAndFinishActivity(LoginActivity.this, RegistrationActivity.class);

    }

    @Override
    public void movetoHome() {
//        NavigationUtils.startAndFinishActivity(LoginActivity.this, HomeActivity.class);\
        finish();
    }

    TextView.OnEditorActionListener actionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                Utils.hideSoftKeyBoard(LoginActivity.this);
                mPresenter.onLoginClicked();
                return true;
            }
            return false;
        }
    };

    public void showShareDialog() {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_share, null);
            builder.setView(dialogView);

            RelativeLayout LayRating = (RelativeLayout) dialogView.findViewById(R.id.LayRating);
            Button mBtnok = (Button) dialogView.findViewById(R.id.btnOk);
            ImageView imgRate = (ImageView) dialogView.findViewById(R.id.imgRate);
            imgRate.setVisibility(View.GONE);
            Button mBtnCancel = (Button) dialogView.findViewById(R.id.btnCancel);
            TextView txtRate = (TextView) dialogView.findViewById(R.id.mTxtRate);
            final EditText mail = (EditText) dialogView.findViewById(R.id.mEdtMailid);
            String mRate = "Please enter your registered e-mail";
            String start = "Do you want to ";
            String end = " this article";
            String complete = start + mRate + end;
//            SpannableString styledString = new SpannableString(complete);
//            styledString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), start.length(), start.length() + mRate.length(), 0);
            txtRate.setText(mRate);
            mBtnok.setText("Submit");
            mBtnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                    mPresenter.onForgetPassword(mail.getText().toString().trim());

                }
            });
            mBtnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();

                }
            });
            mDialog = builder.create();
            mDialog.setCancelable(true);
            mDialog.show();
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Window window = mDialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.verticalMargin = .1f;
            window.setAttributes(wlp);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
