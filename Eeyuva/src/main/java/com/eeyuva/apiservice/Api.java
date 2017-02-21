package com.eeyuva.apiservice;


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
import com.eeyuva.screens.profile.model.NotificationResponse;
import com.eeyuva.screens.profile.model.ProfileResponse;
import com.eeyuva.screens.registration.RegistrationResponse;
import com.eeyuva.screens.searchpage.model.SearchResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by hari on 22/6/16.
 */
public interface Api {
    @GET("user_mlogin/")
    Call<LoginResponse> getAuthentication(@Query("username") String name, @Query("password") String pass, @Query("appid") String appid);

    @GET("usermobileregister/")
    Call<RegistrationResponse> getRegistration(@Query("firstname") String firstName, @Query("lastname") String lastName, @Query("gender") String gender, @Query("email") String email,
                                               @Query("password") String password);

    @GET
    Call<ModuleOrderResponse> getModule(@Url String url);

    @GET
    Call<GetArticleResponse> getArticles(@Url String url);

    @GET
    Call<HotModuleResponse> getHotNews(@Url String url);

    @GET
    Call<ArticleDetailResponse> getArticlesDetails(@Url String url);

    @GET
    Call<SearchResponse> getSearchResponse(@Url String url);

    @GET
    Call<PhotoListResponse> getPhotoList(@Url String url);

    @GET
    Call<PhotoGalleryResponse> getPhotoGalleryList(@Url String url);

    @GET
    Call<LikeDislikeResponse> setLikeorDislike(@Url String url);

    @GET
    Call<CommentPostResponse> getPostComments(@Url String url);

    @GET
    Call<CommentListResponse> getCommentlist(@Url String url);

    @GET
    Call<UserNewsListResponse> geUserNews(@Url String url);

    @GET
    Call<ProfileResponse> getProfile(@Url String url);

    @GET
    Call<EditResponse> getEditProfile(@Url String url);

    @GET
    Call<AlertResponse> getUserAlert(@Url String url);

    @GET
    Call<CommentResponse> getStuffComments(@Url String url);

    @GET
    Call<NewsResponse> getStuffNews(@Url String url);

    @POST
    Call<EditResponse> uploadProfileImage(@Url String url, @Query("uid") String uid, @Query("picdata") String bitmapImg);

    @POST
    Call<ImageResponse> uploadImageVideo(@Url String url, @Body ImageFile encodedString);

    @GET
    Call<NotificationResponse> getNotificationComments(@Url String url);

    @GET
    Call<LoginResponse> getAuthentication(@Url String s);

    @GET
    Call<SmallServerResponse> postShare(@Url String url);

    @GET
    Call<ChangePasswordResponse> ChangePassword(@Url String url);


//    @POST("driver/deviceid/")
//    Call<FCMRegisterResponse> updateFCMToken(@Body FCMRegisterRequest fcmRegisterRequest);
//
//    @DELETE("driver/deviceid/")
//    Call<FCMRegisterResponse> removeFCMToken();

}
