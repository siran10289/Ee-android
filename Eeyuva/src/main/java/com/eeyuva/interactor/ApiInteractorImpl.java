package com.eeyuva.interactor;


import android.util.Log;
import android.widget.EditText;

import com.eeyuva.apiservice.Api;
import com.eeyuva.base.BaseView;
import com.eeyuva.base.LoadListener;
import com.eeyuva.base.UiCallback;
import com.eeyuva.screens.DetailPage.ArticleDetailResponse;
import com.eeyuva.screens.DetailPage.SmallServerResponse;
import com.eeyuva.screens.DetailPage.model.CommentListResponse;
import com.eeyuva.screens.DetailPage.model.CommentPostResponse;
import com.eeyuva.screens.DetailPage.model.LikeDislikeResponse;
import com.eeyuva.screens.authentication.LoginResponse;
import com.eeyuva.screens.gridpages.model.PhotoGalleryResponse;
import com.eeyuva.screens.gridpages.model.PhotoListResponse;
import com.eeyuva.screens.gridpages.model.UserNewsListResponse;
import com.eeyuva.screens.home.GetArticleResponse;
import com.eeyuva.screens.home.HotModuleResponse;
import com.eeyuva.screens.home.ImageFile;
import com.eeyuva.screens.home.ImageResponse;
import com.eeyuva.screens.home.ModuleOrderResponse;
import com.eeyuva.screens.profile.model.AlertResponse;
import com.eeyuva.screens.profile.model.ChangePasswordResponse;
import com.eeyuva.screens.profile.model.CommentResponse;
import com.eeyuva.screens.profile.model.EditResponse;
import com.eeyuva.screens.profile.model.NewsResponse;
import com.eeyuva.screens.profile.model.NotificationEditResponse;
import com.eeyuva.screens.profile.model.NotificationResponse;
import com.eeyuva.screens.profile.model.ProfileResponse;
import com.eeyuva.screens.registration.RegistrationResponse;
import com.eeyuva.screens.searchpage.model.SearchResponse;
import com.eeyuva.utils.preferences.PrefsManager;

import java.io.File;

import retrofit2.Call;


/**
 * Created by hari on 22/6/16.
 */
public class ApiInteractorImpl implements ApiInteractor {

    PrefsManager mPrefsManager;
    Api mApi;

    public ApiInteractorImpl(Api remoteService, PrefsManager mPrefsManager) {
        this.mApi = remoteService;
        this.mPrefsManager = mPrefsManager;
    }

    @Override
    public void getLoginResponse(BaseView mView, String name, String pass, String appid, LoadListener<LoginResponse> mLoginListener) {
        UiCallback<LoginResponse> appConfigUiCallback = new UiCallback(mView, mLoginListener, true);
        Call<LoginResponse> appConfigCall = mApi.getAuthentication("http://www.eeyuva.com/user_mlogin/?username=" + name + "&password=" + pass + "&appid=" + appid);
        Log.e("LoginInputUrl:","http://www.eeyuva.com/user_mlogin/?username=" + name + "&password=" + pass + "&appid=" + appid);
        appConfigUiCallback.start(appConfigCall);
    }

    @Override
    public void getRegistrationResponse(BaseView mView, String firstName, String lastName, String gender,
                                        String email, String password, LoadListener<RegistrationResponse> mRegisterListener) {
        UiCallback<RegistrationResponse> callback = new UiCallback(mView, mRegisterListener, true);
        Call<RegistrationResponse> call = mApi.getRegistration(firstName, lastName, gender, email, password);
        callback.start(call);
    }

    @Override
    public void getModuleResponse(BaseView mView, String url, LoadListener<ModuleOrderResponse> mModuleListener, boolean b) {
        UiCallback<ModuleOrderResponse> callback = new UiCallback(mView, mModuleListener, b);
        Call<ModuleOrderResponse> call = mApi.getModule(url);
        callback.start(call);
    }

    @Override
    public void getArticlesResponse(BaseView mView, String url, LoadListener<GetArticleResponse> mArticlesListener, boolean state) {
        UiCallback<GetArticleResponse> callback = new UiCallback(mView, mArticlesListener, state);
        Call<GetArticleResponse> call = mApi.getArticles(url);
        callback.start(call);
    }

    @Override
    public void getHotModuleResponse(BaseView mView, String url, LoadListener<HotModuleResponse> mHotModuleListener, boolean b) {
        UiCallback<HotModuleResponse> callback = new UiCallback(mView, mHotModuleListener, b);
        Call<HotModuleResponse> call = mApi.getHotNews(url);
        callback.start(call);
    }

    @Override
    public void getArticlesDetails(BaseView mView, String url, LoadListener<ArticleDetailResponse> mArticleListener) {
        UiCallback<ArticleDetailResponse> callback = new UiCallback(mView, mArticleListener, true);
        Call<ArticleDetailResponse> call = mApi.getArticlesDetails(url);
        Log.e("URL:",url.toString());
        callback.start(call);
    }

    @Override
    public void getOtherArticlesDetails(BaseView mView, String url, LoadListener<ArticleDetailResponse> mOtherArticleListener) {
        UiCallback<ArticleDetailResponse> callback = new UiCallback(mView, mOtherArticleListener, false);
        Call<ArticleDetailResponse> call = mApi.getArticlesDetails(url);
        callback.start(call);
    }

    @Override
    public void getSearchResponse(BaseView mView, String url, LoadListener<SearchResponse> mArticlesListener, boolean b) {
        UiCallback<SearchResponse> callback = new UiCallback(mView, mArticlesListener, true);
        Call<SearchResponse> call = mApi.getSearchResponse(url);
        callback.start(call);
    }

    @Override
    public void getPhotoList(BaseView mView, String url, LoadListener<PhotoListResponse> mPhotoListListener) {
        UiCallback<PhotoListResponse> callback = new UiCallback(mView, mPhotoListListener, true);
        Call<PhotoListResponse> call = mApi.getPhotoList(url);
        callback.start(call);
    }

    @Override
    public void getPhotoGalleryList(BaseView mView, String url, LoadListener<PhotoGalleryResponse> mPhotoGalleryListListener) {
        UiCallback<PhotoGalleryResponse> callback = new UiCallback(mView, mPhotoGalleryListListener, true);
        Call<PhotoGalleryResponse> call = mApi.getPhotoGalleryList(url);
        callback.start(call);
    }

    @Override
    public void setLikeorDislike(BaseView mView, String url, LoadListener<LikeDislikeResponse> mOtherArticleListener) {
        UiCallback<LikeDislikeResponse> callback = new UiCallback(mView, mOtherArticleListener, true);
        Call<LikeDislikeResponse> call = mApi.setLikeorDislike(url);
        callback.start(call);
    }

    @Override
    public void getViewComments(BaseView mView, String url, LoadListener<CommentListResponse> mOtherArticleListener) {
        UiCallback<CommentListResponse> callback = new UiCallback(mView, mOtherArticleListener, true);
        Call<CommentListResponse> call = mApi.getCommentlist(url);
        callback.start(call);
    }

    @Override
    public void getPostComments(BaseView mView, String url, LoadListener<CommentPostResponse> mCommentListArticleListener) {
        UiCallback<CommentPostResponse> callback = new UiCallback(mView, mCommentListArticleListener, true);
        Call<CommentPostResponse> call = mApi.getPostComments(url);
        callback.start(call);
    }

    @Override
    public void getUserList(BaseView mView, String url, LoadListener<UserNewsListResponse> mUserNewsListListener) {
        UiCallback<UserNewsListResponse> callback = new UiCallback(mView, mUserNewsListListener, true);
        Call<UserNewsListResponse> call = mApi.geUserNews(url);
        callback.start(call);
    }

    @Override
    public void getProfile(BaseView mView, String url, LoadListener<ProfileResponse> mProfileListener) {
        UiCallback<ProfileResponse> callback = new UiCallback(mView, mProfileListener, true);
        Call<ProfileResponse> call = mApi.getProfile(url);
        callback.start(call);
    }

    @Override
    public void getEditProfile(BaseView mView, String url, LoadListener<EditResponse> mEditProfileListener) {
        Log.e("EditProfileUrl:",url.toString());
        UiCallback<EditResponse> callback = new UiCallback(mView, mEditProfileListener, true);
        Call<EditResponse> call = mApi.getEditProfile(url);
        callback.start(call);
    }

    @Override
    public void getUserAlerts(BaseView mView, String url, LoadListener<AlertResponse> mAlertListner) {
        UiCallback<AlertResponse> callback = new UiCallback(mView, mAlertListner, true);
        Call<AlertResponse> call = mApi.getUserAlert(url);
        callback.start(call);
    }

    @Override
    public void getStuffComments(BaseView mView, String url, LoadListener<CommentResponse> mCommentListener) {
        UiCallback<CommentResponse> callback = new UiCallback(mView, mCommentListener, true);
        Call<CommentResponse> call = mApi.getStuffComments(url);
        callback.start(call);
    }

    @Override
    public void getStuffNews(BaseView mView, String url, LoadListener<NewsResponse> mNewsListener) {
        Log.e("MyStuffNewsURL:",url);
        UiCallback<NewsResponse> callback = new UiCallback(mView, mNewsListener, true);
        Call<NewsResponse> call = mApi.getStuffNews(url);
        callback.start(call);
    }

    @Override
    public void deleteStuffNews(BaseView mView, String url, LoadListener<NotificationEditResponse> mNewsListener) {
        Log.e("MyStuffNewsURL:",url);
        UiCallback<NotificationEditResponse> callback = new UiCallback(mView, mNewsListener, true);
        Call<NotificationEditResponse> call = mApi.deleteStuffNews(url);
        callback.start(call);
    }

    @Override
    public void getNotificationComments(BaseView mView, String url, LoadListener<NotificationResponse> mNotificationListener) {
        Log.e("GetNotificationURL:",url);
        UiCallback<NotificationResponse> callback = new UiCallback(mView, mNotificationListener, true);
        Call<NotificationResponse> call = mApi.getNotificationComments(url);
        callback.start(call);
    }

    @Override
    public void uploadImage(BaseView mView, String url, String uid, String bitmapImg, LoadListener<EditResponse> mEditProfileListener) {
        UiCallback<EditResponse> callback = new UiCallback(mView, mEditProfileListener, true);
        Call<EditResponse> call = mApi.uploadProfileImage(url, uid, bitmapImg);
        callback.start(call);
    }

    @Override
    public void changePassword(BaseView mView, String url, LoadListener<ChangePasswordResponse> mChangePasswordListener) {
        UiCallback<ChangePasswordResponse> callback = new UiCallback(mView, mChangePasswordListener, true);
        Call<ChangePasswordResponse> call = mApi.ChangePassword(url);
        callback.start(call);
    }

    @Override
    public void uploadImageVideo(BaseView mView, String url, ImageFile encodedString, File file, LoadListener<ImageResponse> mEditProfileListener) {
        Log.e("CreatePost:",url);
        Log.e("EncodedImageFile:",encodedString.getmImageString());
        UiCallback<ImageResponse> callback = new UiCallback(mView, mEditProfileListener, true);
        Call<ImageResponse> call = mApi.uploadImageVideo(url, encodedString);
        callback.start(call);
    }

    @Override
    public void getUpdateNotification(BaseView mView, String url, LoadListener<NotificationEditResponse> mCommentListArticleListener) {
        Log.e("NotificationSet URL:",url);
        UiCallback<NotificationEditResponse> callback = new UiCallback(mView, mCommentListArticleListener, true);
        Call<NotificationEditResponse> call = mApi.setNotificationModules(url);
        callback.start(call);
    }

    @Override
    public void updateFCmToken(String url, LoadListener<EditResponse> updateListener) {
        UiCallback<EditResponse> callback = new UiCallback(null, updateListener, true);
        Call<EditResponse> call = mApi.getEditProfile(url);
        callback.start(call);
    }

    @Override
    public void setforgetpassword(BaseView mView, String url, LoadListener<EditResponse> mEditProfileListener) {
        UiCallback<EditResponse> callback = new UiCallback(null, mEditProfileListener, true);
        Call<EditResponse> call = mApi.getEditProfile(url);
        callback.start(call);
    }

    @Override
    public void postShareDetail(BaseView mView, String url, LoadListener<SmallServerResponse> mEditProfileListener) {
        UiCallback<SmallServerResponse> callback = new UiCallback(null, mEditProfileListener, true);
        Call<SmallServerResponse> call = mApi.postShare(url);
        callback.start(call);
    }


}
