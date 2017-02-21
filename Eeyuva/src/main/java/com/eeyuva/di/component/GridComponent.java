package com.eeyuva.di.component;

import com.eeyuva.di.scope.PerActivity;
import com.eeyuva.screens.gridpages.GridContract;
import com.eeyuva.screens.gridpages.GridHomeActivity;
import com.eeyuva.di.module.GridModule;
import com.eeyuva.screens.gridpages.PhotoGalleryActivity;
import com.eeyuva.screens.gridpages.PhotoListActivity;
import com.eeyuva.screens.gridpages.UserNewsActivity;
import com.eeyuva.screens.gridpages.VideoGalleryActivity;
import com.eeyuva.screens.gridpages.VideoListActivity;

import dagger.Component;

/**
 * Created by hari on 17/09/16.
 */
@PerActivity
@Component(
        dependencies = AppComponent.class,
        modules = {GridModule.class})
public interface GridComponent {

    void inject(GridHomeActivity gridHomeActivity);

    void inject(PhotoListActivity photoListActivity);

    void inject(PhotoGalleryActivity photoGalleryActivity);

    void inject(VideoGalleryActivity videoGalleryActivity);

    void inject(VideoListActivity videoListActivity);

    void inject(UserNewsActivity userNewsActivity);

    GridContract.Presenter gridPresenter();
}