package com.eeyuva.screens.splash;

import com.eeyuva.base.BasePresenter;
import com.eeyuva.base.BaseView;

/**
 * Created by kavi on 18/07/16.
 */
public interface SplashContract {

    interface View extends BaseView {
        void setLoadAnim();

        void setVersionNo();

        void showErrorMessage(int id);
        void moveToLogin();
        void moveToDashboard();
    }

    interface Presenter extends BasePresenter {

        void getHomeModule();

        void moveForward();
    }


}
