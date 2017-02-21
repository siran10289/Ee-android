package com.eeyuva.fcm;

/**
 * Created by kavi on 16/08/16.
 */
public interface FCMContract {

    interface Presenter {
        void updateTokenToServer(String token);
    }

}
