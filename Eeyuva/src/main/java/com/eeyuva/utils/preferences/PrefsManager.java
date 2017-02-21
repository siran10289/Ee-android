package com.eeyuva.utils.preferences;


import com.eeyuva.screens.authentication.LoginResponse;
import com.eeyuva.screens.gridpages.model.PhotoGalleryResponse;
import com.eeyuva.screens.home.HotModuleResponse;
import com.eeyuva.screens.home.ModuleOrderResponse;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public interface PrefsManager {

    int NO_USER_ID = -1;

    String SHARED_PREFERENCES_KEY = "com.eeyuva.shared_preferences";

    String SHARED_PREFERENCES_REMOTE_KEY = "fetchr";
    String PREFS_ACCESS_TOKEN = "UserAuthToken";
    String PREFS_USER_NAME = "UserName";
    String PREFS_USER_DETAILS = "UserName_Detail";
    String PREFS_MODULE_DETAILS = "Module_Detail";
    String PREFS_NOTI_MODULE_DETAILS = "Noti_Module_Detail";
    String PREFS_HOT_MODULE_DETAILS = "Hot_Module_Detail";
    String PREFS_PHOTO_GALLERY_DETAILS = "photo_gallery_list";
    String PREFS_FCM_TOKEN = "fcm_token";
    String SHARED_LOCATION_LAT = "lat";
    String SHARED_LOCATION_LNG = "lng";

    String getAccessToken();

    void setAccessToken(String token);


    void setFcmToken(String token);

    String getFcmToken();

    ModuleOrderResponse getModules();

    LatLng getLastLocation();

    void setLastLocation(LatLng clientLocation);

    void setUserDetail(LoginResponse responseBody);
    LoginResponse getUserDetails();

    void setModules(ModuleOrderResponse responseBody);

    void setHotModules(HotModuleResponse responseBody);

    HotModuleResponse getHotModules();

    void setPhotoGalleryList(PhotoGalleryResponse responseBody);

    PhotoGalleryResponse getPhotoGalleryList();

    void setNotificationModules(String mModuleId);
    String getNotificationModules();
}
