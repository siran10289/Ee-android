package com.eeyuva.di.module;

import com.eeyuva.di.scope.PerActivity;
import com.eeyuva.fcm.FCMContract;
import com.eeyuva.fcm.FCMPresenterImpl;
import com.eeyuva.interactor.ApiInteractor;
import com.eeyuva.utils.preferences.PrefsManager;
import com.google.firebase.iid.FirebaseInstanceIdService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kavi on 18/08/16.
 */

@Module
@PerActivity
public class FCMModule extends FCMServiceModule{

    public FCMModule(FirebaseInstanceIdService service) {
        super(service);
    }

    @Provides
    @PerActivity
    FCMContract.Presenter fcmPresenter(ApiInteractor driverInteractor, PrefsManager prefsManager) {
        return new FCMPresenterImpl(driverInteractor, prefsManager);
    }


}

