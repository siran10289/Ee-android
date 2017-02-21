package com.eeyuva.screens.splash;

import android.content.Intent;

import com.eeyuva.base.LoadListener;
import com.eeyuva.interactor.ApiInteractor;
import com.eeyuva.screens.home.HotModuleResponse;
import com.eeyuva.screens.home.ModuleOrderResponse;
import com.eeyuva.utils.Constants;
import com.eeyuva.utils.preferences.PrefsManager;
import com.eeyuva.base.BaseView;

/**
 * Created by kavi on 18/07/16.
 */
public class SplashPresenterImpl implements SplashContract.Presenter {

    PrefsManager mPrefsManager;

    SplashContract.View mView;

    ApiInteractor mApiInteractor;

    public SplashPresenterImpl(ApiInteractor mApiInteractor, PrefsManager prefsManager) {
        this.mApiInteractor = mApiInteractor;
        this.mPrefsManager = prefsManager;
    }

    @Override
    public void getHomeModule() {
        mApiInteractor.getModuleResponse(mView, Constants.SplashHomeModule, mModuleListener, false);
    }

    @Override
    public void moveForward() {
        getHomeModule();
    }

    @Override
    public void setView(BaseView view) {
        mView = (SplashContract.View) view;
        mView.setVersionNo();
        mView.setLoadAnim();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    LoadListener<ModuleOrderResponse> mModuleListener = new LoadListener<ModuleOrderResponse>() {
        @Override
        public void onSuccess(ModuleOrderResponse responseBody) {
            mPrefsManager.setModules(responseBody);
            mApiInteractor.getHotModuleResponse(mView, Constants.SplashHotModule, mHotModuleListener, false);


        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };

    LoadListener<HotModuleResponse> mHotModuleListener = new LoadListener<HotModuleResponse>() {
        @Override
        public void onSuccess(HotModuleResponse responseBody) {
            mPrefsManager.setHotModules(responseBody);
            mView.moveToDashboard();
        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };

}
