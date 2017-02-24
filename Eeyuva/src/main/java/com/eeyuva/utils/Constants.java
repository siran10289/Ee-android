package com.eeyuva.utils;

/**
 * Created by hari on 22/6/16.
 */
public class Constants {

    public static final String DOMAINNAME = "http://mobile.eeyuva.com/";
    public static final String DEVDOMAINNAME = "http://www.eeyuva.com/";

    public static final int REQUEST_CAPTURE_PHOTO = 5;
    public static final int REQUEST_GALLERY_PHOTO = 6;
    public static final int REQUEST_CODE_CLOSE = 100;

    public static final String SplashHomeModule = getDomainName() + "moduleorder.json";
    public static final String SplashHotModule = getDomainName() + "getheaderservice.php";
    public static final String DetailGetArticleInfo = getDomainName() + "getarticleinfo.php?";
    public static final String DetailGetRelatedArticles = getDomainName() + "getrelatedarticles.php?";
    public static final String DetailLikeDislike = getDevDomainName() + "mlike_dislike/?";
    public static final String DetailfetchUserCommments = getDomainName() + "fetchusercomments.php?";
    public static final String DetailPostComments = getDomainName() + "postcomments.php?";
    public static final String DetailModuleWiseUserDetails = getDomainName() + "getmodulewiseusernewsdetails.php?";
    public static final String DetailGetUserDetail = getDomainName() + "getusernewsdetails.php?";
    public static final String DetailPostShareDetail = getDevDomainName() + "mshare/?";
    public static final String DetailPostUserNews = getDomainName() + "postusernews.php?";
    public static final String GridGetPhotoGallery = getDomainName() + "getphotogallery.php?";
    public static final String GridGetPhotoAlbum = getDomainName() + "getphotoalbums.php?";
    public static final String GridGetUserNews = getDomainName() + "getmodusernews.php?";
    public static final String GridGetVideoGallery = getDomainName() + "getvideogallery.php?";
    public static final String GridGetVideoAlbum = getDomainName() + "getvideoalbums.php?";
    public static final String HomeGetModule = getDomainName() + "moduleorder.json";
    public static final String HomeGetArticle = getDomainName() + "getarticles.php?";
    public static final String HomeSearchInfo = getDomainName() + "getsearchinfo.php?";
    public static final String ProfileGetUserInfo = getDomainName() + "getuserinfo.php?";
    public static final String ProfileEditUserInfo = getDomainName() + "edituserinfo.php?";
    public static final String ProfileUserAlerts = getDomainName() + "fetchalerts.php?";
    public static final String ProfileGetUserCommments = getDomainName() + "getusercommentsinfo.php?";
    public static final String ProfileGetUserNews = getDomainName() + "getusernews.php/?";
    public static final String ProfileGetUserNotification = getDomainName() + "fetchuserpush.php/?";
    public static final String ProfileUpdatePhoto = getDomainName() + "editprofilepic.php";
    public static final String ProfileChangePassword = getDevDomainName() + "user_mchangepassword/?";
    public static final String ProfilePostUserNews = getDomainName() + "postusernews.php?";
    public static final String ProfileFetchUserComments = getDomainName() + "fetchusercomments.php?";
    public static final String ProfileUpdateNotification = getDomainName() + "pushsettings.php?";
    public static final String AppUpdate= getDomainName() + "getappid.php?";
    public static final String DeleteUserNews = getDomainName() + "delusernews.php/?";

    public static final int PROFILE = 2000;
    public static final int ALERT = 2001;
    public static final int CHANGEPASSWORD = 2002;
    public static final int STUFFS = 2003;
    public static final int DETAIL = 2004;
    public static final int GRID = 2005;
    public static final int PHOTOGALLERY = 2006;
    public static final int PHOTOLIST = 2007;
    public static final int USERNEWS=2008;
    public static final int VIDEOGALLERY=2009;
    public static final int VIDEOLIST=2010;
    public static final int HOME=2011;
    public static final int ARTICLES=2011;
    public static final int SEARCH=2012;

    public static String TAG_Article_ID = "articleid";
    public static String TAG_Module_ID = "modid";
    public static String TAG_Module_Name = "modulename";
    public static String TAG_Notification = "noti";


    public static String getDomainName() {
        return DOMAINNAME;
    }

    public static String getDevDomainName() {
        return DEVDOMAINNAME;
    }

    public static String BUNDLE = "bunlde";
    public static String MESSAGE_DATA = "message";
    public static String MESSAGE_TAG = "tag";
    public static final String DISPLAY_MESSAGE_ACTION = "com.fetchr.delivrEx.fcm.DISPLAY_MESSAGE";

}
