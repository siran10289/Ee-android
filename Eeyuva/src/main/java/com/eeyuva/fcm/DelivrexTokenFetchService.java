package com.eeyuva.fcm;

/**
 * Created by kavi on 14/08/16.
 */

import android.util.Log;

import com.eeyuva.Application;
import com.eeyuva.di.component.DaggerFCMComponent;
import com.eeyuva.di.component.FCMComponent;
import com.eeyuva.di.module.FCMModule;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import javax.inject.Inject;


public class DelivrexTokenFetchService extends FirebaseInstanceIdService {

    @Inject
    FCMContract.Presenter mPresenter;

    private FCMComponent component;



    private static final String TAG = "MyFirebaseIIDService";


    @Override
    public void onCreate() {
        super.onCreate();
        initComponent();
    }

    public DelivrexTokenFetchService() {
    }

    private void initComponent() {
        component = DaggerFCMComponent.builder()
                .appComponent(Application.getAppComponent())
                .fCMModule(new FCMModule(this))
                .build();
        component.inject(this);
    }


    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);

    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        mPresenter.updateTokenToServer(token);
        // TODO: Implement this method to send token to your app server.
    }
}