package com.eeyuva.screens.registration;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.eeyuva.ButterAppCompatActivity;
import com.eeyuva.R;
import com.eeyuva.di.component.DaggerLoginComponent;
import com.eeyuva.di.component.DaggerRegistrationComponent;
import com.eeyuva.di.component.RegistrationComponent;
import com.eeyuva.di.module.LoginPresenterModule;
import com.eeyuva.di.module.RegistrationPresenterModule;
import com.eeyuva.screens.authentication.LoginActivity;
import com.eeyuva.screens.home.HomeActivity;
import com.eeyuva.utils.navigation.NavigationUtils;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by hari on 05/09/16.
 */
public class RegistrationActivity extends ButterAppCompatActivity implements RegistrationContract.View {
    @Inject
    RegistrationContract.Presenter mPresenter;

    RegistrationComponent mComponent;

    @Bind(R.id.mEdtFirstName)
    EditText mEdtFirstName;

    @Bind(R.id.mEdtLastName)
    EditText mEdtLastName;

    @Bind(R.id.mEdtMailid)
    EditText mEdtMailid;

    @Bind(R.id.mEdtPassword)
    EditText mEdtPassword;

    @Bind(R.id.mEdtConfirmPassword)
    EditText mEdtConfirmPassword;

    @Bind(R.id.mBtnSignup)
    Button mBtnSignup;

    @Bind(R.id.mBtnLogin)
    Button mBtnLogin;

    @Bind(R.id.radioSex)
    RadioGroup radioSexGroup;

    RadioButton radioSexButton;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initComponent();
        mPresenter.setView(this);
    }

    private void initComponent() {
        mComponent = DaggerRegistrationComponent.builder()
                .appComponent(getApplicationComponent())
                .registrationPresenterModule(new RegistrationPresenterModule(this))
                .build();
        mComponent.inject(this);
    }

    @OnClick(R.id.mBtnLogin)
    public void onLoginClick() {
        mPresenter.onLoginClicked();

    }

    @OnClick(R.id.mBtnSignup)
    public void onSignupClick() {
        int selectedId = radioSexGroup.getCheckedRadioButtonId();
        radioSexButton = (RadioButton) findViewById(selectedId);
        mPresenter.onSignupClicked();

    }

    @Override
    public void movetoLogin() {
        NavigationUtils.startAndFinishActivity(RegistrationActivity.this, LoginActivity.class);

    }


    @Override
    public String getFirstName() {
        return mEdtFirstName.getText().toString().trim();
    }

    @Override
    public String getLastName() {
        return mEdtLastName.getText().toString().trim();
    }

    @Override
    public String getEmail() {
        return mEdtMailid.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return mEdtPassword.getText().toString().trim();
    }

    @Override
    public String getConfirmPassword() {
        return mEdtConfirmPassword.getText().toString().trim();
    }

    @Override
    public String getGender() {
        try {
            Log.i("sex","sex"+radioSexButton.getText());
            if (radioSexButton.getText().toString().equalsIgnoreCase("male"))
                return "0";
            else if (radioSexButton.getText().toString().equalsIgnoreCase("female"))
                return "1";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

}
