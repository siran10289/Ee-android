package com.eeyuva.screens.profile;

import android.app.Activity;

import com.eeyuva.di.module.ActivityModule;
import com.eeyuva.di.scope.PerActivity;
import com.eeyuva.interactor.ApiInteractor;
import com.eeyuva.screens.gridpages.GridContract;
import com.eeyuva.screens.gridpages.GridPresenterImpl;
import com.eeyuva.screens.profile.userdetails.interactor.PackageInfoInteractor;
import com.eeyuva.utils.preferences.PrefsManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hari on 01/10/16.
 */
@Module
@PerActivity
public class ProfileModule extends ActivityModule {
    public ProfileModule(Activity activity) {
        super(activity);
    }

    @Provides
    @PerActivity
    public ProfileContract.Presenter profilePresenter(ApiInteractor apiInteractor, PrefsManager prefsManager,PackageInfoInteractor mPackageInfoInteractor) {
        return new ProfilePresenterImpl(apiInteractor, prefsManager,mPackageInfoInteractor);
    }

}
