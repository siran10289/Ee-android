package com.eeyuva.screens.DetailPage;

import android.app.Activity;
import android.content.Intent;
import android.util.Base64;
import android.util.Log;

import com.eeyuva.base.BaseView;
import com.eeyuva.base.LoadListener;
import com.eeyuva.interactor.ApiInteractor;
import com.eeyuva.screens.DetailPage.model.CommentListResponse;
import com.eeyuva.screens.DetailPage.model.CommentPostResponse;
import com.eeyuva.screens.DetailPage.model.LikeDislikeResponse;
import com.eeyuva.screens.authentication.LoginResponse;
import com.eeyuva.screens.home.CatagoryList;
import com.eeyuva.screens.home.ImageFile;
import com.eeyuva.screens.home.ImageResponse;
import com.eeyuva.screens.home.ResponseList;
import com.eeyuva.screens.profile.userdetails.interactor.ImageProcessingListener;
import com.eeyuva.screens.profile.userdetails.interactor.PackageInfoInteractor;
import com.eeyuva.screens.profile.userdetails.interactor.PermissionGrantedListener;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hari on 14/09/16.
 */
public class DetailPresenterImpl implements DetailContract.Presenter {

    private PrefsManager mPrefsManager;
    private ApiInteractor mApiInteractor;
    private DetailContract.View mView;
    private PackageInfoInteractor mPackageInfoInteractor;
    private String mLikeType;


    public DetailPresenterImpl(ApiInteractor apiInteractor, PrefsManager prefsManager, PackageInfoInteractor packageInfoInteractor) {
        this.mApiInteractor = apiInteractor;
        this.mPrefsManager = prefsManager;
        this.mPackageInfoInteractor = packageInfoInteractor;
    }

    @Override
    public void setView(BaseView view) {
        mView = (DetailContract.View) view;
    }

    @Override
    public void getArticlesDetails(String mModuleId, String mArticleId) {
        mApiInteractor.getArticlesDetails(mView, Constants.DetailGetArticleInfo + "moduleid=" + mModuleId + "&arid=" + mArticleId, mArticleListener);

    }

    @Override
    public void getOtherArticlesDetails(String mModuleId, String mArticleId) {
        mApiInteractor.getOtherArticlesDetails(mView, Constants.DetailGetRelatedArticles + "articleid=" + mArticleId + "&modid=" + mModuleId, mOtherArticleListener);
    }

    @Override
    public List<ResponseList> getModules() {
        return mPrefsManager.getModules().getResponse();
    }

    @Override
    public void setLikeOrDislike(String article_id, String type, String module_id, String uid) {
        mLikeType = type;
        mApiInteractor.setLikeorDislike(mView, Constants.DetailLikeDislike + "article_id=" + article_id + "&type=" + type + "&module_id=" + module_id + "&uid=" + mPrefsManager.getUserDetails().getUserid(), mDislikeListener);
    }

    @Override
    public void getViewComments(String mModuleId, String articleid) {
        mApiInteractor.getViewComments(mView, Constants.DetailfetchUserCommments + "modid=" + mModuleId + "&eid=" + articleid, mCommentListArticleListener);
    }

    @Override
    public void setPostComments(String trim, String mModuleId, String articleid) {
        if (trim.length() != 0 && trim != null)
            mApiInteractor.getPostComments(mView, Constants.DetailPostComments + "uid=" + mPrefsManager.getUserDetails().getUserid() + "&modid="
                    + mModuleId + "&eid=" + articleid + "&msg=" + trim, mCommentPostListener);
        else
            mView.showListenerDialog("Please enter your comments.");

    }

    @Override
    public void getArticlesDetails(String mArticleId) {
        mApiInteractor.getArticlesDetails(mView, Constants.DetailModuleWiseUserDetails + "arid=" + mArticleId, mArticleListener);

    }

    @Override
    public void getOtherArticlesDetails(String mModuleId, String mArticleId, String mEntityId) {
        mApiInteractor.getOtherArticlesDetails(mView, Constants.DetailGetRelatedArticles + "articleid=" + mArticleId + "&modid=" + mModuleId + "&ufl=" + mEntityId, mOtherArticleListener);
    }

    @Override
    public void getArticlesNewsDetails(String mArticleId) {
        mApiInteractor.getArticlesDetails(mView, Constants.DetailGetUserDetail + "uid=" + mPrefsManager.getUserDetails().getUserid() + "&arid=" + mArticleId, mArticleListener);

    }


    LoadListener<ArticleDetailResponse> mArticleListener = new LoadListener<ArticleDetailResponse>() {
        @Override
        public void onSuccess(ArticleDetailResponse responseBody) {
            if (responseBody.getResponse().size() != 0)
                Log.e("ArticalDetail:",new Gson().toJson(responseBody).toString());
                mView.setArticleDetails(responseBody.getResponse().get(0));
        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };
    LoadListener<ArticleDetailResponse> mOtherArticleListener = new LoadListener<ArticleDetailResponse>() {
        @Override
        public void onSuccess(ArticleDetailResponse responseBody) {
            mView.setOtherArticleDetails(responseBody.getResponse());
        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };
    LoadListener<CommentListResponse> mCommentListArticleListener = new LoadListener<CommentListResponse>() {
        @Override
        public void onSuccess(CommentListResponse responseBody) {
            if (responseBody.getStatusInfo().equalsIgnoreCase("No comments")) {
                mView.setAdpaterNotComments();
                mView.showErrorDialog("Comment not avaiable.");
            } else
                mView.setCommentsListToAdapter(responseBody.getResponse());
        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };

    LoadListener<CommentPostResponse> mCommentPostListener = new LoadListener<CommentPostResponse>() {
        @Override
        public void onSuccess(CommentPostResponse responseBody) {
            mView.showErrorDialog(responseBody.getStatusInfo());
        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };

    LoadListener<LikeDislikeResponse> mDislikeListener = new LoadListener<LikeDislikeResponse>() {
        @Override
        public void onSuccess(LikeDislikeResponse responseBody) {
            if (mLikeType.equalsIgnoreCase("1")) {
                if (responseBody.getStatusInfo().equalsIgnoreCase("success"))
                    mView.setLikeCount(responseBody.getCountLike());
                else
                    mView.showErrorDialog("You have already performed this activity");
            } else {
                if (responseBody.getStatusInfo().equalsIgnoreCase("success"))
                    mView.setDisLikeCount(responseBody.getCountLike());
                else
                    mView.showErrorDialog("You have already performed this activity");
            }


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

    @Override
    public LoginResponse getUserdetails() {
        return mPrefsManager.getUserDetails();
    }

    @Override
    public void postShareDetail(String mModuleId, String mEntityId, String mail) {

        if (isValidEmail(mail))
            mApiInteractor.postShareDetail(mView, Constants.DetailPostShareDetail + "module_id=" + mModuleId + "&entity_id=" + mEntityId + "&uid=" + mPrefsManager.getUserDetails().getUserid() + "&email=" + mail, mServerListener);
        else
            mView.showErrorDialog("Please enter valid e-mail address.");

    }


    private boolean isValidEmail(String email) {// validation for email Id
        boolean isValid = false;
        String expression = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            isValid = true;

        }
        return isValid;
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

        byte[] fbytes = Base64.decode(encodedString, Base64.DEFAULT);
        BigInteger bigInt = new BigInteger(fbytes);
        String hexString = bigInt.toString(16);

        System.out.println(hexString);

        ImageFile imagefile = new ImageFile(hexString);

        mApiInteractor.uploadImageVideo(mView, Constants.DetailPostUserNews + "mid=4&catid=Cat_6395ebd0f&title=" + title + "&desc=" + desc + "&uid=" + mPrefsManager.getUserDetails().getUserid(), imagefile,photoFile, mEditProfileListener);

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

    LoadListener<SmallServerResponse> mServerListener = new LoadListener<SmallServerResponse>() {
        @Override
        public void onSuccess(SmallServerResponse responseBody) {
            mView.showErrorDialog(responseBody.getStatusInfo());
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



}
