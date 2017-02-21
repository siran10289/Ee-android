package com.eeyuva.di.module;

import android.app.Activity;

import com.eeyuva.di.module.ActivityModule;
import com.eeyuva.di.scope.PerActivity;
import com.eeyuva.interactor.ApiInteractor;
import com.eeyuva.screens.DetailPage.DetailContract;
import com.eeyuva.screens.DetailPage.DetailPresenterImpl;
import com.eeyuva.screens.profile.userdetails.interactor.PackageInfoInteractor;
import com.eeyuva.utils.preferences.PrefsManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hari on 14/09/16.
 */

@Module
@PerActivity
public class DetailModule extends ActivityModule {

    public DetailModule(Activity activity) {
        super(activity);
    }

    @Provides
    @PerActivity
    public DetailContract.Presenter detailspresenter(ApiInteractor driverInteractor, PrefsManager prefsManager, PackageInfoInteractor packageInfoInteractor) {
        return new DetailPresenterImpl(driverInteractor,prefsManager,packageInfoInteractor);
    }
}