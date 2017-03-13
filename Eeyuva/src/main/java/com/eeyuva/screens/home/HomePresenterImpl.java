package com.eeyuva.screens.home;

import android.app.Activity;
import android.content.Intent;
import android.util.Base64;
import android.util.Log;

import com.eeyuva.base.BaseView;
import com.eeyuva.base.LoadListener;
import com.eeyuva.interactor.ApiInteractor;
import com.eeyuva.screens.authentication.LoginResponse;
import com.eeyuva.screens.profile.model.EditResponse;
import com.eeyuva.screens.profile.userdetails.interactor.ImageProcessingListener;
import com.eeyuva.screens.profile.userdetails.interactor.PackageInfoInteractor;
import com.eeyuva.screens.profile.userdetails.interactor.PermissionGrantedListener;
import com.eeyuva.screens.searchpage.model.SearchResponse;
import com.eeyuva.utils.Constants;
import com.eeyuva.utils.preferences.PrefsManager;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by hari on 05/09/16.
 */
public class HomePresenterImpl implements HomeContract.Presenter {

    private PrefsManager mPrefsManager;
    private ApiInteractor mApiInteractor;
    private HomeContract.View mView;
    private PackageInfoInteractor mPackageInfoInteractor;


    public HomePresenterImpl(ApiInteractor apiInteractor, PrefsManager prefsManager, PackageInfoInteractor packageInfoInteractor) {
        this.mApiInteractor = apiInteractor;
        this.mPrefsManager = prefsManager;
        this.mPackageInfoInteractor = packageInfoInteractor;
    }

    @Override
    public void setView(BaseView view) {
        this.mView = (HomeContract.View) view;
    }

    @Override
    public void getHomeModule() {
        mApiInteractor.getModuleResponse(mView, Constants.HomeGetModule, mModuleListener, false);
    }

    @Override
    public void getArticles(String moduleid) {
        mApiInteractor.getArticlesResponse(mView, Constants.HomeGetArticle + "moduleid=" + moduleid + "&startindex=0&endindex=10", mArticlesListener, true);

    }

    @Override
    public void getSearchResponse(String key) {
        mApiInteractor.getSearchResponse(mView, Constants.HomeSearchInfo + "search_key=" + key, mLoadSearchListener, false);

    }

    @Override
    public void getArticles(String moduleid, int index, int i) {
        mApiInteractor.getArticlesResponse(mView, Constants.HomeGetArticle + "moduleid=" + moduleid + "&startindex=" + index + "&endindex=10", mLoadArticlesListener, false);
    }

    @Override
    public List<ResponseList> getModules() {
        return mPrefsManager.getModules().getResponse();
    }

    @Override
    public List<ModuleList> getHotModules() {
        return mPrefsManager.getHotModules().getModuleList();
    }

    @Override
    public LoginResponse getUserdetails() {
        return mPrefsManager.getUserDetails();
    }

    LoadListener<ModuleOrderResponse> mModuleListener = new LoadListener<ModuleOrderResponse>() {
        @Override
        public void onSuccess(ModuleOrderResponse responseBody) {
            mView.setDataToAdapter(responseBody.getResponse());
        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };

    LoadListener<GetArticleResponse> mArticlesListener = new LoadListener<GetArticleResponse>() {
        @Override
        public void onSuccess(GetArticleResponse responseBody) {
            Log.e("Response:",new Gson().toJson(responseBody).toString());
            mView.setArticleAdapterNotify(responseBody.getResponseItem());
        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };

    LoadListener<GetArticleResponse> mLoadArticlesListener = new LoadListener<GetArticleResponse>() {
        @Override
        public void onSuccess(GetArticleResponse responseBody) {
            mView.setLoadMoredata(responseBody);
        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };

    LoadListener<SearchResponse> mLoadSearchListener = new LoadListener<SearchResponse>() {
        @Override
        public void onSuccess(SearchResponse responseBody) {
            mView.setLoadMoredata(responseBody);
        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };
    @Override
    public void getCatagoryDetails(String moduleID) {
        mApiInteractor.getCatagoryList(mView, Constants.GetCatagoryList + "modid=" + moduleID, mGetCatagoryListListener);

    }
    LoadListener<CatagoryList> mGetCatagoryListListener = new LoadListener<CatagoryList>() {
        @Override
        public void onSuccess(CatagoryList catagoryList) {
             mView.setCatagoryList(catagoryList);
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
        mView.openActivityForResult(mPackageInfoInteractor.getPhotoCaptureIntent(), Constants.REQUEST_CAPTURE_PHOTO);
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

    @Override
    public void setClearPrefs() {
        LoginResponse mLogin=new LoginResponse();
        mPrefsManager.setUserDetail(mLogin);
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
    public void uploadImageOrVideo(File photoFile, String modulename, String title, String desc,String moduleID,String catID) {
//

        InputStream inputStream = null;//You can get an inputStream using any IO API
        try {
            inputStream = new FileInputStream(photoFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] bytes;
        byte[] buffer = new byte[20000];
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

        byte[] fbytes = Base64.decode(encodedString, Base64.DEFAULT);
        BigInteger bigInt = new BigInteger(fbytes);
        String hexString = bigInt.toString(16);

        System.out.println(hexString);

        ImageFile imagefile = new ImageFile(hexString);

        mApiInteractor.uploadImageVideo(mView, Constants.DetailPostUserNews + "mid="+moduleID+"&catid="+catID+"&title=" + title + "&desc=" + desc + "&uid="+mPrefsManager.getUserDetails().getUserid(), imagefile,photoFile, mEditProfileListener);
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
