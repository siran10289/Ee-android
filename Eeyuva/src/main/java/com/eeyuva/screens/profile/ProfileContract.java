package com.eeyuva.screens.profile;

import android.content.Intent;

import com.eeyuva.base.BasePresenter;
import com.eeyuva.base.BaseView;
import com.eeyuva.screens.DetailPage.model.CommentsList;
import com.eeyuva.screens.authentication.LoginResponse;
import com.eeyuva.screens.home.CatagoryList;
import com.eeyuva.screens.home.ResponseList;
import com.eeyuva.screens.profile.model.AlertList;
import com.eeyuva.screens.profile.model.CommentResponse;
import com.eeyuva.screens.profile.model.NewsResponse;
import com.eeyuva.screens.profile.model.NotificationResponse;
import com.eeyuva.screens.profile.model.ProfileResponse;

import java.io.File;
import java.util.List;

/**
 * Created by hari on 01/10/16.
 */

public interface ProfileContract {
    interface View extends BaseView {
        void setImage(String url);

        void setUserDetails(ProfileResponse responseBody);

        void setAdapter(List<AlertList> alertList);

        void setCommentAdapter(CommentResponse responseBody);

        void setNewsAdapter(NewsResponse responseBody);

        void setNotificationAdapter(NotificationResponse responseBody);
        void setPhoto(File photoFile);
        void setCommentsListToAdapter(List<CommentsList> response);
        void goToLogin();
        void updateSaveModules(String notificationModules);
        void showArticleDeletedStatus(String message);
        void setCatagoryList(CatagoryList catagoryListPojo);
        void showProfileImageUpdateAlert(String message);
        void showAlertForNoficationSettings(String message);

    }

    interface Presenter extends BasePresenter {
        List<ResponseList> getModules();

        void getProfile();

        void setUpdateProfile(String fname, String lastname, String mobile, String gender, String dob, String about,int mnflag,int dobflag);

        void getUserAlerts();

        void getComments();

        void getNews();

        void getNotification();

        void snapPhotoClick();

        void pickFromGalleryClick();

        void onActivityResult(int requestCode, int resultCode, Intent intent);

        void uploadImage(File photoFile);

        void setChangePassword(String oldpass, String newpass, String conpass);

        void uploadImageOrVideo(File photoFile, String trim, String s, String trim1,String moduleID,String catID);

        void getViewComments(String moduleid, String artid);

        LoginResponse getUserdetails();

        void updateNotification(String moduleId);

        void getSaveNotification();
        void deleteMyArticle(String articleID);
        void getCatagoryDetails(String moduleID);
    }

    interface AdapterCallBack {

        void getComments(String moduleid, String artid);
    }
}
