package com.eeyuva.screens.authentication;

import com.eeyuva.base.BasePresenter;
import com.eeyuva.base.BaseView;

/**
 * Created by hari on 22/6/16.
 */
public interface LoginContract {

    interface View extends BaseView {
        String getUsername();

        String getPassword();

        void movetoSignUp();

        void movetoHome();
    }

    interface Presenter extends BasePresenter {
        void onLoginClicked();

        boolean validateAuthentication(String name, String pass);

        boolean validateUsername(String name);

        boolean validatePassword(String pass);

        void onSignupClick();

        void onForgetPassword(String trim);
    }
}
