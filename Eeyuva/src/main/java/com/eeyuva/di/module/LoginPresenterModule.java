package com.eeyuva.di.module;

import android.app.Activity;

import com.eeyuva.di.scope.PerActivity;
import com.eeyuva.interactor.ApiInteractor;
import com.eeyuva.screens.authentication.LoginPresenterImpl;
import com.eeyuva.utils.preferences.PrefsManager;
import com.eeyuva.screens.authentication.LoginContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hari on 23/6/16.
 * <p/>
 * This is a Dagger module. We use this to pass in the dependencies to the Loginpresenter.
 */
@Module
@PerActivity
public class LoginPresenterModule extends ActivityModule {

    public LoginPresenterModule(Activity activity) {
        super(activity);
    }

    @Provides
    @PerActivity
    public LoginContract.Presenter loginPresenter(ApiInteractor driverInteractor, PrefsManager prefsManager) {
        return new LoginPresenterImpl(driverInteractor,prefsManager);
    }
}
