package com.eeyuva.screens.DetailPage;

import android.content.Intent;

import com.eeyuva.base.BasePresenter;
import com.eeyuva.base.BaseView;
import com.eeyuva.screens.DetailPage.model.CommentsList;
import com.eeyuva.screens.authentication.LoginResponse;
import com.eeyuva.screens.home.CatagoryList;
import com.eeyuva.screens.home.ResponseList;

import java.io.File;
import java.util.List;

/**
 * Created by hari on 14/09/16.
 */
public interface DetailContract {
    interface View extends BaseView {

        void setArticleDetails(ArticleDetail articleDetail);

        void setOtherArticleDetails(List<ArticleDetail> response);

        void setCommentsListToAdapter(List<CommentsList> response);

        void setPhoto(File photoFile);

        void setLikeCount(Integer countLike);

        void setDisLikeCount(Integer countLike);

        void setAdpaterNotComments();

        void setCatagoryList(CatagoryList catagoryListPojo);


    }

    interface Presenter extends BasePresenter {

        void getArticlesDetails(String mModuleId, String mArticleId);

        void getOtherArticlesDetails(String mModuleId, String mArticleId);

        List<ResponseList> getModules();

        void setLikeOrDislike(String article_id, String type, String module_id, String uid);

        void getViewComments(String mModuleId, String articleid);

        void setPostComments(String trim, String mModuleId, String articleid);

        void getArticlesDetails(String mArticleId);

        void getOtherArticlesDetails(String mModuleId, String mArticleId, String mEntityId);

        void getArticlesNewsDetails(String mArticleId);

        void uploadImageOrVideo(File photoFile, String trim, String s, String trim1,String moduleID,String catID);

        void pickFromGalleryClick();

        void snapPhotoClick();

        void onActivityResult(int requestCode, int resultCode, Intent data);

        LoginResponse getUserDetails();

        LoginResponse getUserdetails();

        void postShareDetail(String mModuleId, String mEntityId, String trim);
        void getCatagoryDetails(String moduleID);
    }

    public interface AdapterCallBack {
    }
}
