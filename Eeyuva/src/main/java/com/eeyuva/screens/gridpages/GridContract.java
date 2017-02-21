package com.eeyuva.screens.gridpages;

import android.content.Intent;

import com.eeyuva.base.BasePresenter;
import com.eeyuva.base.BaseView;
import com.eeyuva.screens.authentication.LoginResponse;
import com.eeyuva.screens.gridpages.model.PhotoGalleryList;
import com.eeyuva.screens.gridpages.model.PhotoGalleryResponse;
import com.eeyuva.screens.gridpages.model.PhotoList;
import com.eeyuva.screens.gridpages.model.PhotoListResponse;
import com.eeyuva.screens.gridpages.model.UserNewsList;
import com.eeyuva.screens.gridpages.model.UserNewsListResponse;
import com.eeyuva.screens.home.ResponseList;

import java.io.File;
import java.util.List;

/**
 * Created by hari on 17/09/16.
 */
public interface GridContract {

    interface View extends BaseView {

        void setAdapter(PhotoListResponse responseBody);

        void moveToGalleryView();

        void setAdapter(PhotoGalleryResponse responseBody);

        void setAdapter(UserNewsListResponse responseBody);

        void setPhoto(File photoFile);

    }

    interface Presenter extends BasePresenter {
        List<ResponseList> getModules();

        void getPhotoList(String url, String mModuleId);

        void getPhotoGalleryList(String url, String trid);

        void getVideoGalleryList(String url, String trid);

        List<PhotoGalleryList> getPhotoGallery();

        void getUserList(String s, String mModuleId);

        void uploadImageOrVideo(File photoFile, String trim, String s, String trim1);

        void pickFromGalleryClick();

        void snapPhotoClick();

        void onActivityResult(int requestCode, int resultCode, Intent data);

        LoginResponse getUserDetails();


    }

    interface AdapterCallBack {
        void setSelectItem(ResponseList rl);

        void setSelectItem(PhotoList rl);

        void setSelectItem(PhotoGalleryList rl);

        void setSelectItem(UserNewsList rl);
    }
}
