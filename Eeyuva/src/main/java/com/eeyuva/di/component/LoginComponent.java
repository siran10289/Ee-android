package com.eeyuva.di.component;

import com.eeyuva.di.module.LoginPresenterModule;
import com.eeyuva.di.scope.PerActivity;
import com.eeyuva.screens.authentication.LoginActivity;
import com.eeyuva.screens.authentication.LoginContract;

import dagger.Component;

/**
 * Created by hari on 23/6/16.
 */

@PerActivity
@Component(
        dependencies = AppComponent.class,
        modules = {LoginPresenterModule.class})
public interface LoginComponent {

    void inject(LoginActivity loginActivity);

    LoginContract.Presenter loginMethodPresenter();
}
