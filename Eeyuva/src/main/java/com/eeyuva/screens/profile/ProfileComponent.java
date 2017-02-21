package com.eeyuva.screens.profile;

import com.eeyuva.di.component.AppComponent;
import com.eeyuva.di.scope.PerActivity;
import com.eeyuva.screens.profile.alerts.AlertActivity;
import com.eeyuva.screens.profile.notification.NotificationActivity;
import com.eeyuva.screens.profile.stuffs.StuffsActivity;
import com.eeyuva.screens.profile.userdetails.ProfileActivity;

import dagger.Component;

/**
 * Created by hari on 01/10/16.
 */
@PerActivity
@Component(
        dependencies = AppComponent.class,
        modules = {ProfileModule.class})
public interface ProfileComponent {

    void inject(ProfileActivity profileActivity);

    void inject(ChangePasswordActivity changePasswordActivity);

    void inject(AlertActivity alertActivity);

    void inject(StuffsActivity stuffsActivity);

    void inject(NotificationActivity notificationActivity);

    ProfileContract.Presenter profilePresenter();
}