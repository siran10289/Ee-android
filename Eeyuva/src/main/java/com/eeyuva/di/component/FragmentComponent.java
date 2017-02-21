package com.eeyuva.di.component;

import android.support.v4.app.FragmentActivity;

import com.eeyuva.di.module.FragmentModule;
import com.eeyuva.di.scope.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(FragmentActivity fragmentActivity);

    //Exposed to sub-graphs.
    FragmentActivity fragmentActivity();
}