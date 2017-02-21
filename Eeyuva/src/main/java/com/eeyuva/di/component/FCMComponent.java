package com.eeyuva.di.component;

import com.eeyuva.di.module.FCMModule;
import com.eeyuva.di.scope.PerActivity;
import com.eeyuva.fcm.DelivrexTokenFetchService;
import com.eeyuva.fcm.FCMContract;

import dagger.Component;

/**
 * Created by kavi on 18/08/16.
 */

@PerActivity
@Component(dependencies = AppComponent.class,modules = FCMModule.class)
public interface FCMComponent {
    void inject(DelivrexTokenFetchService service);

    FCMContract.Presenter fcmIdUpdateService();

}