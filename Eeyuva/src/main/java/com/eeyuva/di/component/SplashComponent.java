package com.eeyuva.di.component;

import com.eeyuva.di.module.SplashPresenterModule;
import com.eeyuva.di.scope.PerActivity;
import com.eeyuva.screens.splash.SplashActivity;
import com.eeyuva.screens.splash.SplashContract;

import dagger.Component;

/**
 * Created by kavi on 18/07/16.
 */
@PerActivity
@Component(
        dependencies = AppComponent.class,
        modules = {SplashPresenterModule.class})
public interface SplashComponent {

    void inject(SplashActivity splashActivity);

    SplashContract.Presenter splashMethodPresenter();
}
