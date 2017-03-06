package com.eeyuva.screens.gridpages;

import android.app.Activity;
import android.content.Intent;
import android.util.Base64;

import com.eeyuva.base.BaseView;
import com.eeyuva.base.LoadListener;
import com.eeyuva.interactor.ApiInteractor;
import com.eeyuva.screens.authentication.LoginResponse;
import com.eeyuva.screens.gridpages.model.PhotoGalleryList;
import com.eeyuva.screens.gridpages.model.PhotoGalleryResponse;
import com.eeyuva.screens.gridpages.model.PhotoListResponse;
import com.eeyuva.screens.gridpages.model.UserNewsListResponse;
import com.eeyuva.screens.home.ImageFile;
import com.eeyuva.screens.home.ImageResponse;
import com.eeyuva.screens.home.ResponseList;
import com.eeyuva.screens.profile.userdetails.interactor.ImageProcessingListener;
import com.eeyuva.screens.profile.userdetails.interactor.PackageInfoInteractor;
import com.eeyuva.screens.profile.userdetails.interactor.PermissionGrantedListener;
import com.eeyuva.utils.Constants;
import com.eeyuva.utils.preferences.PrefsManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by hari on 17/09/16.
 */
public class GridPresenterImpl implements GridContract.Presenter {
    ApiInteractor mApiInteractor;
    PrefsManager mPrefsManager;
    GridContract.View mView;
    private PackageInfoInteractor mPackageInfoInteractor;


    public GridPresenterImpl(ApiInteractor apiInteractor, PrefsManager prefsManager, PackageInfoInteractor packageInfoInteractor) {
        this.mApiInteractor = apiInteractor;
        this.mPrefsManager = prefsManager;
        this.mPackageInfoInteractor = packageInfoInteractor;

    }

    @Override
    public void setView(BaseView view) {
        this.mView = (GridContract.View) view;
    }

    @Override
    public List<ResponseList> getModules() {
        return mPrefsManager.getModules().getResponse();
    }

    @Override
    public void getPhotoList(String url, String mModuleId) {
        mApiInteractor.getPhotoList(mView, url+"modid=" + mModuleId, mPhotoListListener);
    }

    @Override
    public void getPhotoGalleryList(String url, String trid) {
        mApiInteractor.getPhotoGalleryList(mView, url+"galid=" + trid, mPhotoGalleryListListener);
    }

    @Override
    public void getVideoGalleryList(String url, String trid) {
        mApiInteractor.getPhotoGalleryList(mView, url+"galid=" + trid, mVideoGalleryListListener);
    }

    @Override
    public List<PhotoGalleryList> getPhotoGallery() {
        return mPrefsManager.getPhotoGalleryList().getResponse();
    }

    @Override
    public void getUserList(String url, String mModuleId) {
        mApiInteractor.getUserList(mView, url, mUserNewsListListener);

    }

    LoadListener<UserNewsListResponse> mUserNewsListListener = new LoadListener<UserNewsListResponse>() {
        @Override
        public void onSuccess(UserNewsListResponse responseBody) {
            mView.setAdapter(responseBody);
        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    }; LoadListener<PhotoListResponse> mPhotoListListener = new LoadListener<PhotoListResponse>() {
        @Override
        public void onSuccess(PhotoListResponse responseBody) {
            mView.setAdapter(responseBody);
        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };

    LoadListener<PhotoGalleryResponse> mPhotoGalleryListListener = new LoadListener<PhotoGalleryResponse>() {
        @Override
        public void onSuccess(PhotoGalleryResponse responseBody) {
            mPrefsManager.setPhotoGalleryList(responseBody);
            mView.moveToGalleryView();
        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };

    LoadListener<PhotoGalleryResponse> mVideoGalleryListListener = new LoadListener<PhotoGalleryResponse>() {
        @Override
        public void onSuccess(PhotoGalleryResponse responseBody) {
            mView.setAdapter(responseBody);
        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };



    @Override
    public void snapPhotoClick() {
        if (mPackageInfoInteractor.checkForCameraPermission()) {
            capturePhotoFromCamera();
        } else {
            mPackageInfoInteractor.getPermissions(cameraPermissionGrantedListener);
        }
    }

    private void capturePhotoFromCamera() {
        mView.openActivityForResult(mPackageInfoInteractor.getPhotoCaptureIntent(),
                Constants.REQUEST_CAPTURE_PHOTO);
    }

    @Override
    public void pickFromGalleryClick() {
        if (mPackageInfoInteractor.checkForCameraPermission()) {
            selectPictureFromGallery();
        } else {
            mPackageInfoInteractor.getPermissions(galleryPermissionGrantedListener);
        }
    }

    private void selectPictureFromGallery() {
        mView.openActivityForResult(Intent.createChooser(mPackageInfoInteractor
                .getPhotoGalleryIntent(), "Choose photo"), Constants.REQUEST_GALLERY_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.REQUEST_GALLERY_PHOTO) {
                mPackageInfoInteractor.handleGalleryPhoto(data.getData(), imageProcessingListener);
            } else if (requestCode == Constants.REQUEST_CAPTURE_PHOTO) {
                mPackageInfoInteractor.handleCapturedPhoto(imageProcessingListener);
            } else if (requestCode == Constants.REQUEST_CODE_CLOSE) {
                closeActivityOnResult(data);
            }
        }
    }

    @Override
    public LoginResponse getUserDetails() {
        return mPrefsManager.getUserDetails();
    }

    private void closeActivityOnResult(Intent data) {
        mView.setResultAndCloseActivity(data);
    }


    private ImageProcessingListener imageProcessingListener = new ImageProcessingListener() {
        @Override
        public void onProcessingStart() {
            mView.showProgress();
        }

        @Override
        public void onProcessingComplete() {
            mView.hideProgress();
            mView.setPhoto(mPackageInfoInteractor.getPhotoFile());

        }
    };


    private PermissionGrantedListener cameraPermissionGrantedListener =
            new PermissionGrantedListener() {
                @Override
                public void onPermissionDenied() {

                }

                @Override
                public void onPermissionGranted() {
                    capturePhotoFromCamera();
                }
            };

    private PermissionGrantedListener galleryPermissionGrantedListener =
            new PermissionGrantedListener() {
                @Override
                public void onPermissionDenied() {

                }

                @Override
                public void onPermissionGranted() {
                    selectPictureFromGallery();
                }
            };

    @Override
    public void uploadImageOrVideo(File photoFile, String modulename, String title, String desc) {
//

        InputStream inputStream = null;//You can get an inputStream using any IO API
        try {
            inputStream = new FileInputStream(photoFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] bytes;
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        bytes = output.toByteArray();
        String encodedString = Base64.encodeToString(bytes, Base64.DEFAULT);

        byte[] fbytes = Base64.decode(encodedString,Base64.DEFAULT);
        BigInteger bigInt = new BigInteger(fbytes);
        String hexString = bigInt.toString(16);

        System.out.println(hexString);

        ImageFile imagefile = new ImageFile(hexString);

        mApiInteractor.uploadImageVideo(mView, Constants.DetailPostUserNews+"mid=4&catid=Cat_6395ebd0f&title=" + title + "&desc=" + desc + "&uid=3939", imagefile,photoFile, mEditProfileListener);
    }


    LoadListener<ImageResponse> mEditProfileListener = new LoadListener<ImageResponse>() {
        @Override
        public void onSuccess(ImageResponse responseBody) {
            mView.showErrorDialog(responseBody.getStatusResponse());
        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };

}
