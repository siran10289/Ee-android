package com.eeyuva.di.module;

import android.app.Activity;

import com.eeyuva.interactor.ApiInteractor;
import com.eeyuva.screens.splash.SplashContract;
import com.eeyuva.di.scope.PerActivity;
import com.eeyuva.screens.splash.SplashPresenterImpl;
import com.eeyuva.utils.preferences.PrefsManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kavi on 18/07/16.
 */

@Module
@PerActivity
public class SplashPresenterModule extends ActivityModule{

    public SplashPresenterModule(Activity activity) {
        super(activity);
    }

    @Provides
    @PerActivity
    public SplashContract.Presenter splashPresenter(ApiInteractor driverInteractor, PrefsManager prefsManager) {
        return new SplashPresenterImpl(driverInteractor,prefsManager);
    }
}
