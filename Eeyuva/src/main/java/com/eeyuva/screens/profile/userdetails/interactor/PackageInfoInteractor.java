package com.eeyuva.screens.profile.userdetails.interactor;

import android.content.Intent;
import android.net.Uri;
import java.io.File;

/**
 * Created by sceacal on 5/6/16.
 */
public interface PackageInfoInteractor {

    Intent getPhotoCaptureIntent();

    Intent getPhotoGalleryIntent();

    boolean checkForCameraPermission();

    void getPermissions(PermissionGrantedListener permissionGrantedListener);

    void removeTempFiles();

    void handleCapturedPhoto(ImageProcessingListener processingListener);

    void handleGalleryPhoto(Uri uri, ImageProcessingListener processingListener);

    File getPhotoFile();

    String getBitmapImg();

}
