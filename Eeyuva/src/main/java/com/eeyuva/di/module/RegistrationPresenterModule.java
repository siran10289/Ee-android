package com.eeyuva.di.module;

import android.app.Activity;

import com.eeyuva.di.scope.PerActivity;
import com.eeyuva.interactor.ApiInteractor;
import com.eeyuva.screens.registration.RegistrationContract;
import com.eeyuva.screens.registration.RegistrationPresenterImpl;
import com.eeyuva.utils.preferences.PrefsManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hari on 23/6/16.
 * <p/>
 * This is a Dagger module. We use this to pass in the dependencies to the Loginpresenter.
 */
@Module
@PerActivity
public class RegistrationPresenterModule extends ActivityModule {

    public RegistrationPresenterModule(Activity activity) {
        super(activity);
    }

    @Provides
    @PerActivity
    public RegistrationContract.Presenter loginPresenter(ApiInteractor driverInteractor, PrefsManager prefsManager) {
        return new RegistrationPresenterImpl(driverInteractor,prefsManager);
    }
}
