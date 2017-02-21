package com.eeyuva.di.component;

import com.eeyuva.di.scope.PerActivity;
import com.eeyuva.screens.DetailPage.DetailActivity;
import com.eeyuva.screens.DetailPage.DetailContract;
import com.eeyuva.di.module.DetailModule;

import dagger.Component;

/**
 * Created by hari on 14/09/16.
 */

@PerActivity
@Component(
        dependencies = AppComponent.class,
        modules = {DetailModule.class})
public interface DetailComponent {

    void inject(DetailActivity homeActivity);

    DetailContract.Presenter detailPresenter();
}