package com.eeyuva.di.component;

import com.eeyuva.di.module.LoginPresenterModule;
import com.eeyuva.di.module.RegistrationPresenterModule;
import com.eeyuva.di.scope.PerActivity;
import com.eeyuva.screens.authentication.LoginActivity;
import com.eeyuva.screens.authentication.LoginContract;
import com.eeyuva.screens.registration.RegistrationActivity;
import com.eeyuva.screens.registration.RegistrationContract;

import dagger.Component;

/**
 * Created by hari on 23/6/16.
 */

@PerActivity
@Component(
        dependencies = AppComponent.class,
        modules = {RegistrationPresenterModule.class})
public interface RegistrationComponent {

    void inject(RegistrationActivity loginActivity);

    RegistrationContract.Presenter presenter();
}
