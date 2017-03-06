package com.eeyuva.interactor;


import com.eeyuva.base.BaseView;
import com.eeyuva.base.LoadListener;
import com.eeyuva.fcmregister.FCMRegisterRequest;
import com.eeyuva.fcmregister.FCMRegisterResponse;
import com.eeyuva.screens.DetailPage.ArticleDetailResponse;
import com.eeyuva.screens.DetailPage.DetailContract;
import com.eeyuva.screens.DetailPage.SmallServerResponse;
import com.eeyuva.screens.DetailPage.model.CommentListResponse;
import com.eeyuva.screens.DetailPage.model.CommentPostResponse;
import com.eeyuva.screens.DetailPage.model.LikeDislikeResponse;
import com.eeyuva.screens.authentication.LoginContract;
import com.eeyuva.screens.authentication.LoginResponse;
import com.eeyuva.screens.gridpages.GridContract;
import com.eeyuva.screens.gridpages.model.PhotoGalleryResponse;
import com.eeyuva.screens.gridpages.model.PhotoListResponse;
import com.eeyuva.screens.gridpages.model.UserNewsListResponse;
import com.eeyuva.screens.home.GetArticleResponse;
import com.eeyuva.screens.home.HomeContract;
import com.eeyuva.screens.home.HotModuleResponse;
import com.eeyuva.screens.home.ImageFile;
import com.eeyuva.screens.home.ImageResponse;
import com.eeyuva.screens.home.ModuleOrderResponse;
import com.eeyuva.screens.profile.ProfileContract;
import com.eeyuva.screens.profile.model.AlertResponse;
import com.eeyuva.screens.profile.model.ChangePasswordResponse;
import com.eeyuva.screens.profile.model.CommentResponse;
import com.eeyuva.screens.profile.model.EditResponse;
import com.eeyuva.screens.profile.model.NewsResponse;
import com.eeyuva.screens.profile.model.NotificationEditResponse;
import com.eeyuva.screens.profile.model.NotificationResponse;
import com.eeyuva.screens.profile.model.ProfileResponse;
import com.eeyuva.screens.registration.RegistrationContract;
import com.eeyuva.screens.registration.RegistrationResponse;
import com.eeyuva.screens.searchpage.model.SearchResponse;

import java.io.File;

/**
 * Created by hari on 22/6/16.
 */
public interface ApiInteractor {
    void getLoginResponse(BaseView mView, String name, String pass, String appid, LoadListener<LoginResponse> mLoginListener);

    void getRegistrationResponse(BaseView mView, String firstName, String lastName, String email, String password, String confirmPassword, LoadListener<RegistrationResponse> mRegisterListener);

    void getModuleResponse(BaseView mView, String s, LoadListener<ModuleOrderResponse> mModuleListener, boolean b);

    void getArticlesResponse(BaseView mView, String s, LoadListener<GetArticleResponse> mArticlesListener, boolean b);

    void getHotModuleResponse(BaseView mView, String s, LoadListener<HotModuleResponse> mHotModuleListener, boolean b);

    void getArticlesDetails(BaseView mView, String s, LoadListener<ArticleDetailResponse> mArticleListener);

    void getOtherArticlesDetails(BaseView mView, String s, LoadListener<ArticleDetailResponse> mOtherArticleListener);

    void getSearchResponse(BaseView mView, String s, LoadListener<SearchResponse> mArticlesListener, boolean b);

    void getPhotoList(BaseView mView, String s, LoadListener<PhotoListResponse> mPhotoListListener);

    void getPhotoGalleryList(BaseView mView, String s, LoadListener<PhotoGalleryResponse> mPhotoGalleryListListener);

    void setLikeorDislike(BaseView mView, String s, LoadListener<LikeDislikeResponse> mOtherArticleListener);

    void getViewComments(BaseView mView, String s, LoadListener<CommentListResponse> mOtherArticleListener);

    void getPostComments(BaseView mView, String url, LoadListener<CommentPostResponse> mCommentListArticleListener);

    void getUserList(BaseView mView, String url, LoadListener<UserNewsListResponse> mUserNewsListListener);

    void getProfile(BaseView mView, String s, LoadListener<ProfileResponse> mProfileListener);

    void getEditProfile(BaseView mView, String s, LoadListener<EditResponse> mEditProfileListener);

    void getUserAlerts(BaseView mView, String s, LoadListener<AlertResponse> mAlertListner);

    void getStuffComments(BaseView mView, String s, LoadListener<CommentResponse> mCommentListener);
    void getStuffNews(BaseView mView, String s, LoadListener<NewsResponse> mNewsListener);
    void deleteStuffNews(BaseView mView,String url,LoadListener<NotificationEditResponse> mNewsListener);
    void getNotificationComments(BaseView mView, String s, LoadListener<NotificationResponse> mNotificationListener);

    void uploadImage(BaseView mView, String url, String uid, String bitmapImg, LoadListener<EditResponse> mEditProfileListener);

    void changePassword(BaseView mView, String url, LoadListener<ChangePasswordResponse> mChangePasswordListener);

    void uploadImageVideo(BaseView mView, String url, ImageFile encodedString, File file, LoadListener<ImageResponse> mEditProfileListener);

    void getUpdateNotification(BaseView mView, String s, LoadListener<NotificationEditResponse> mCommentListArticleListener);

    void updateFCmToken(String url, LoadListener<EditResponse> updateListener);

    void setforgetpassword(BaseView mView, String s, LoadListener<EditResponse> mEditProfileListener);

    void postShareDetail(BaseView mView, String s, LoadListener<SmallServerResponse> mEditProfileListener);
}

