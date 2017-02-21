package com.eeyuva.di.module;

import android.app.Activity;

import com.eeyuva.di.module.ActivityModule;
import com.eeyuva.di.scope.PerActivity;
import com.eeyuva.interactor.ApiInteractor;
import com.eeyuva.screens.home.HomeContract;
import com.eeyuva.screens.home.HomePresenterImpl;
import com.eeyuva.screens.profile.userdetails.interactor.PackageInfoInteractor;
import com.eeyuva.utils.preferences.PrefsManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hari on 05/09/16.
 */
@Module
@PerActivity
public class HomeModule extends ActivityModule {

    public HomeModule(Activity activity) {
        super(activity);
    }

    @Provides
    @PerActivity
    public HomeContract.Presenter homepresenter(ApiInteractor driverInteractor, PrefsManager prefsManager, PackageInfoInteractor packageInfoInteractor) {
        return new HomePresenterImpl(driverInteractor,prefsManager,packageInfoInteractor);
    }
}