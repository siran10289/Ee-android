package com.eeyuva.di.component;

import android.content.Context;

import com.eeyuva.Application;
import com.eeyuva.apiservice.Api;
import com.eeyuva.di.module.AppModules;
import com.eeyuva.di.module.NetworkModule;
import com.eeyuva.di.scope.GsonRestAdapter;
import com.eeyuva.interactor.ApiInteractor;
import com.eeyuva.screens.profile.userdetails.interactor.PackageInfoInteractor;
import com.eeyuva.utils.preferences.PrefsManager;
import com.eeyuva.di.module.PresentationModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {AppModules.class, NetworkModule.class, PresentationModule.class})
public interface AppComponent {

    void inject(Application driverApplication);

    Context context();

    Api apiHal();

    @GsonRestAdapter
    Api apiGson();

    Retrofit retrofit();

    ApiInteractor driverInteractor();

    PackageInfoInteractor packageInteractor();

    PrefsManager prefsManager();


}
