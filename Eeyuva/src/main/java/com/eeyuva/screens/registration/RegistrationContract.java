package com.eeyuva.screens.registration;

import com.eeyuva.base.BasePresenter;
import com.eeyuva.base.BaseView;

/**
 * Created by hari on 05/09/16.
 */
public interface RegistrationContract {
    interface View extends BaseView {

        void movetoLogin();

        String getFirstName();

        String getLastName();

        String getEmail();

        String getPassword();

        String getConfirmPassword();

        String getGender();
    }

    interface Presenter extends BasePresenter {

        void onLoginClicked();

        void onSignupClicked();
    }
}
