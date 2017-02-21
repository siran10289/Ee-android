package com.eeyuva.di.module;

import android.app.Application;
import android.content.Context;

import com.eeyuva.utils.preferences.PrefsManager;
import com.eeyuva.utils.preferences.PrefsManagerImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModules {
    private Application application;

    public AppModules(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    public Context provideApplication() {
        return application;
    }

    @Singleton
    @Provides
    public PrefsManager providePrefsManager(Context context) {
        return new PrefsManagerImpl(context);
    }
}
