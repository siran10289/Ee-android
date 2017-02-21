package com.eeyuva.di.component;

import android.app.Activity;

import com.eeyuva.di.module.ActivityModule;
import com.eeyuva.di.scope.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(Activity activity);

    //Exposed to sub-graphs.
    Activity activity();
}