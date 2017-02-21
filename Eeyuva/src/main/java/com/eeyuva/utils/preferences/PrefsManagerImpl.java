package com.eeyuva.utils.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.eeyuva.screens.authentication.LoginResponse;
import com.eeyuva.screens.gridpages.model.PhotoGalleryResponse;
import com.eeyuva.screens.home.HotModuleResponse;
import com.eeyuva.screens.home.ModuleOrderResponse;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.pixplicity.easyprefs.library.Prefs;

public class PrefsManagerImpl implements PrefsManager {


    private Context context;
    private Gson gson;

    private SharedPreferences remotePrefs;
    private SharedPreferences.Editor remoteEditor;

    public PrefsManagerImpl(Context context) {
        this.context = context;
        gson = new Gson();
        remotePrefs = context.getSharedPreferences(SHARED_PREFERENCES_REMOTE_KEY, Context.MODE_PRIVATE);
        remoteEditor = remotePrefs.edit();
    }

    @Override
    public String getAccessToken() {
        return remotePrefs.getString(PREFS_ACCESS_TOKEN, null);
    }

    @Override
    public void setAccessToken(String token) {
        remoteEditor.remove(PREFS_ACCESS_TOKEN);
        remoteEditor.putString(PREFS_ACCESS_TOKEN, token);
        remoteEditor.commit();
    }

    @Override
    public void setFcmToken(String token) {
        remoteEditor.remove(PREFS_FCM_TOKEN);
        remoteEditor.putString(PREFS_FCM_TOKEN, token);
        remoteEditor.commit();
    }

    @Override
    public String getFcmToken() {
        return remotePrefs.getString(PREFS_FCM_TOKEN, null);
    }

    @Override
    public void setLastLocation(LatLng lastLocation) {
        if (lastLocation != null) {
            Prefs.putDouble(SHARED_LOCATION_LAT, lastLocation.latitude);
            Prefs.putDouble(SHARED_LOCATION_LNG, lastLocation.longitude);
        }
    }

    @Override
    public void setUserDetail(LoginResponse responseBody) {
        String result = gson.toJson(responseBody);
        remoteEditor.remove(PREFS_USER_DETAILS);
        remoteEditor.putString(PREFS_USER_DETAILS, result);
        remoteEditor.commit();
    }

    @Override
    public LoginResponse getUserDetails() {
        return gson.fromJson(remotePrefs.getString(PREFS_USER_DETAILS, null), LoginResponse.class);
    }

    @Override
    public void setModules(ModuleOrderResponse responseBody) {
        String result = gson.toJson(responseBody);
        remoteEditor.remove(PREFS_MODULE_DETAILS);
        remoteEditor.putString(PREFS_MODULE_DETAILS, result);
        remoteEditor.commit();
    }

    @Override
    public void setHotModules(HotModuleResponse responseBody) {
        String result = gson.toJson(responseBody);
        remoteEditor.remove(PREFS_HOT_MODULE_DETAILS);
        remoteEditor.putString(PREFS_HOT_MODULE_DETAILS, result);
        remoteEditor.commit();
    }

    @Override
    public HotModuleResponse getHotModules() {
        return gson.fromJson(remotePrefs.getString(PREFS_HOT_MODULE_DETAILS, null), HotModuleResponse.class);
    }

    @Override
    public void setPhotoGalleryList(PhotoGalleryResponse responseBody) {
        String result = gson.toJson(responseBody);
        remoteEditor.remove(PREFS_PHOTO_GALLERY_DETAILS);
        remoteEditor.putString(PREFS_PHOTO_GALLERY_DETAILS, result);
        remoteEditor.commit();
    }

    @Override
    public PhotoGalleryResponse getPhotoGalleryList() {
        return gson.fromJson(remotePrefs.getString(PREFS_PHOTO_GALLERY_DETAILS, null), PhotoGalleryResponse.class);
    }

    @Override
    public void setNotificationModules(String mModuleId) {
        remoteEditor.remove(PREFS_NOTI_MODULE_DETAILS);
        remoteEditor.putString(PREFS_NOTI_MODULE_DETAILS, mModuleId);
        remoteEditor.commit();
    }

    @Override
    public String getNotificationModules() {
        return remotePrefs.getString(PREFS_NOTI_MODULE_DETAILS, null);
    }

    @Override
    public ModuleOrderResponse getModules() {
        return gson.fromJson(remotePrefs.getString(PREFS_MODULE_DETAILS, null), ModuleOrderResponse.class);
    }

    @Override
    public LatLng getLastLocation() {
        double lat = Prefs.getDouble(SHARED_LOCATION_LAT, 0);
        double lng = Prefs.getDouble(SHARED_LOCATION_LNG, 0);
        return new LatLng(lat, lng);
    }


}
