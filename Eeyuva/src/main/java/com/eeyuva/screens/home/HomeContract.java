package com.eeyuva.screens.home;

import android.content.Intent;

import com.eeyuva.base.BasePresenter;
import com.eeyuva.base.BaseView;
import com.eeyuva.screens.authentication.LoginResponse;
import com.eeyuva.screens.searchpage.model.SearchResponse;

import java.io.File;
import java.util.List;

/**
 * Created by hari on 05/09/16.
 */
public interface HomeContract {
    interface View extends BaseView {

        void setDataToAdapter(List<ResponseList> response);
        void setArticleAdapterNotify(List<ResponseItem> responseItem);
        void setLoadMoredata(GetArticleResponse responseBody);
        void setLoadMoredata(SearchResponse responseBody);
        void setPhoto(File photoFile);
        void setCatagoryList(CatagoryList catagoryListPojo);
    }

    interface Presenter extends BasePresenter {

        void getHomeModule();

        void getArticles(String moduleid, int index, int i);

        List<ResponseList> getModules();

        List<ModuleList> getHotModules();

        LoginResponse getUserdetails();

        void getArticles(String moduleid);

        void getSearchResponse(String key);

        void uploadImageOrVideo(File photoFile, String trim, String s, String trim1,String moduleID,String catID);

        void pickFromGalleryClick();

        void snapPhotoClick();

        void onActivityResult(int requestCode, int resultCode, Intent data);

        LoginResponse getUserDetails();

        void setClearPrefs();

        void getCatagoryDetails(String moduleID);
    }

    interface AdapterCallBack {
        void onItemClick(String articleid, String modid);
    }
}
