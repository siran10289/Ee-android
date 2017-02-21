package com.eeyuva;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.multidex.MultiDexApplication;

import com.eeyuva.di.component.DaggerAppComponent;
import com.eeyuva.utils.preferences.PrefsManager;
import com.eeyuva.di.component.AppComponent;
import com.eeyuva.di.module.AppModules;
import com.karumi.dexter.Dexter;
import com.pixplicity.easyprefs.library.Prefs;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class Application extends MultiDexApplication {
    public static AppComponent appComponent;
    public static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initAppComponents();
        initPreferences();
        Dexter.initialize(this);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

    public static Application get(Context context) {
        return (Application) context.getApplicationContext();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    public void initAppComponents() {
        appComponent = DaggerAppComponent.builder()
                .appModules(new AppModules(this))
                .build();
    }

    private static void initPreferences() {
        new Prefs.Builder()
                .setContext(instance)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(PrefsManager.SHARED_PREFERENCES_KEY)
                .setUseDefaultSharedPreference(false)
                .build();
    }


    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

    private static boolean activityVisible;
}

