package com.eeyuva.di.component;

import com.eeyuva.di.scope.PerActivity;
import com.eeyuva.screens.abouapp.AboutAppActivity;
import com.eeyuva.screens.home.HomeActivity;
import com.eeyuva.screens.home.HomeContract;
import com.eeyuva.di.module.HomeModule;
import com.eeyuva.screens.home.loadmore.ArticlesActivity;
import com.eeyuva.screens.searchpage.SearchActivity;

import dagger.Component;

/**
 * Created by hari on 05/09/16.
 */

@PerActivity
@Component(
        dependencies = AppComponent.class,
        modules = {HomeModule.class})
public interface HomeComponent {

    void inject(HomeActivity homeActivity);

    void inject(ArticlesActivity articlesActivity);

    void inject(SearchActivity searchActivity);

    void inject(AboutAppActivity aboutAppActivity);

    HomeContract.Presenter homePresenter();
}
