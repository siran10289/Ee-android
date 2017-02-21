package com.eeyuva.screens.profile.userdetails.interactor;

/**
 * Created by sceacal on 2/18/16.
 */
public interface PermissionGrantedListener {

    void onPermissionDenied();

    void onPermissionGranted();
}
