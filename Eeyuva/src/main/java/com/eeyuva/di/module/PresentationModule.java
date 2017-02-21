package com.eeyuva.di.module;

import android.content.Context;

import com.eeyuva.apiservice.Api;
import com.eeyuva.di.scope.GsonRestAdapter;
import com.eeyuva.interactor.ApiInteractor;
import com.eeyuva.interactor.ApiInteractorImpl;
import com.eeyuva.screens.profile.userdetails.interactor.PackageInfoInteractor;
import com.eeyuva.screens.profile.userdetails.interactor.PackageInfoInteractorImpl;
import com.eeyuva.utils.preferences.PrefsManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hari on 10/7/16.
 */
@Module
public class PresentationModule {
    @Provides
    @Singleton
    public ApiInteractor driverInteractor(
            @GsonRestAdapter Api remoteService, PrefsManager prefsManager) {
        return new ApiInteractorImpl(remoteService, prefsManager);
    }

    @Provides
    @Singleton
    public PackageInfoInteractor mPackageInfoInteractor(Context context, PrefsManager prefsManager) {
        return new PackageInfoInteractorImpl(context, prefsManager);
    }


}
