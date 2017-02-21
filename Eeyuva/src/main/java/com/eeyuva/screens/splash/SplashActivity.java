package com.eeyuva.screens.splash;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.eeyuva.BuildConfig;
import com.eeyuva.R;
import com.eeyuva.di.component.DaggerSplashComponent;
import com.eeyuva.di.module.SplashPresenterModule;
import com.eeyuva.screens.DetailPage.DetailActivity;
import com.eeyuva.screens.authentication.LoginActivity;
import com.eeyuva.screens.home.HomeActivity;
import com.eeyuva.utils.Utils;
import com.eeyuva.utils.customdialog.DialogListener;
import com.eeyuva.ButterAppCompatActivity;
import com.eeyuva.di.component.SplashComponent;
import com.eeyuva.utils.Constants;
import com.eeyuva.utils.customdialog.DialogUtils;
import com.eeyuva.utils.navigation.NavigationUtils;
import com.google.firebase.iid.FirebaseInstanceId;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by kavi on 18/07/16.
 */
public class SplashActivity extends ButterAppCompatActivity implements SplashContract.View, Animation.AnimationListener {

    @Inject
    SplashContract.Presenter mPresenter;

    SplashComponent mComponent;

    @Bind(R.id.mTxtVersion)
    TextView mTxtVersion;

    @Bind(R.id.mImgLoader)
    ImageView mImgLoader;

    boolean mIsTablet;

    Animation mImgLoaderAnim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initComponent();
        mIsTablet = getResources().getBoolean(R.bool.isTablet);
        if (mIsTablet) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        mPresenter.setView(this);

        try {
            Log.i("Noti", "Noti" + getIntent().getExtras().getString(Constants.TAG_Notification));
            Log.i("Noti", "Noti" + getIntent().getExtras().getString(Constants.TAG_Article_ID));
            Log.i("Noti", "Noti" + getIntent().getExtras().getString(Constants.TAG_Module_ID));
            if (!getIntent().getExtras().getString(Constants.TAG_Module_ID).equalsIgnoreCase(null) && getIntent().getExtras().getString(Constants.TAG_Module_ID).length() != 0) {
                Intent intent =
                        new Intent(this, DetailActivity.class);
                intent.putExtra("article_id", getIntent().getExtras().getString(Constants.TAG_Article_ID));
                intent.putExtra("module_id", getIntent().getExtras().getString(Constants.TAG_Module_ID));
                intent.putExtra("type", "home");
                startActivity(intent);
            } else if (getIntent().getExtras().getString("status").equalsIgnoreCase("clear")) {
                Intent intent =
                        new Intent(this, SplashActivity.class);
                intent.putExtra("status", "clear");
                startActivity(intent);
                finish();
            } else {
                mPresenter.moveForward();
            }
        } catch (Exception E) {
            E.printStackTrace();
            mPresenter.moveForward();
        }

        FirebaseInstanceId.getInstance().getToken();

    }

    @Override
    public void setLoadAnim() {
        mImgLoaderAnim = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        mImgLoaderAnim.setAnimationListener(this);
        mImgLoaderAnim.setRepeatCount(-1);
        mImgLoaderAnim.setRepeatMode(Animation.INFINITE);
    }

    @Override
    public void setVersionNo() {
        mTxtVersion.setText("v " + BuildConfig.VERSION_NAME);
        mTxtVersion.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mImgLoader.setVisibility(View.VISIBLE);
        mImgLoader.startAnimation(mImgLoaderAnim);
        setUpAnimation();
    }

    private void setUpAnimation() {
        if (!Utils.isOnline(this)) {
            mImgLoader.clearAnimation();
            mImgLoader.setVisibility(View.INVISIBLE);
            return;
        }
        mImgLoader.setVisibility(View.VISIBLE);
    }


    private void initComponent() {
        mComponent = DaggerSplashComponent.builder()
                .appComponent(getApplicationComponent())
                .splashPresenterModule(new SplashPresenterModule(this))
                .build();
        mComponent.inject(this);
    }


    @Override
    public void showErrorMessage(int id) {


    }

    @Override
    public void moveToLogin() {
        NavigationUtils.startAndFinishActivity(SplashActivity.this, LoginActivity.class);
    }

    @Override
    public void moveToDashboard() {
        try {
//            if (!getIntent().getExtras().getString(Constants.TAG_Module_ID).equalsIgnoreCase(null) && getIntent().getExtras().getString(Constants.TAG_Module_ID).length() != 0) {
//                Intent intent =
//                        new Intent(this, HomeActivity.class);
//                intent.putExtra("article_id", getIntent().getExtras().getString(Constants.TAG_Article_ID));
//                intent.putExtra("module_id", getIntent().getExtras().getString(Constants.TAG_Module_ID));
//                intent.putExtra("type", "home");
//                startActivity(intent);
//            } else {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
//            }
        } catch (Exception e) {
            e.printStackTrace();
            Intent intent =
                    new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
    }


    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        mImgLoader.startAnimation(mImgLoaderAnim);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}

