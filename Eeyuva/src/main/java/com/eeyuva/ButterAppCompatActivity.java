package com.eeyuva;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.eeyuva.base.BaseView;
import com.eeyuva.screens.DetailPage.DetailActivity;
import com.eeyuva.screens.authentication.LoginActivity;
import com.eeyuva.screens.home.HomeActivity;
import com.eeyuva.screens.splash.SplashActivity;
import com.eeyuva.utils.Constants;
import com.eeyuva.utils.customdialog.CustomProgressDialog;
import com.eeyuva.utils.customdialog.DialogUtils;
import com.eeyuva.di.component.AppComponent;

import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by hari on 22/6/16.
 */
public class ButterAppCompatActivity extends AppCompatActivity implements BaseView {


    private boolean isActive = true;

    ProgressDialog progressDialog;
    private String TAG = "Message";

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        progressDialog = CustomProgressDialog.getInstance(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        boolean mIsTablet = getResources().getBoolean(R.bool.isTablet);
        if (mIsTablet) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
        MultiDex.install(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    protected AppComponent getApplicationComponent() {
        return Application.getAppComponent();
    }

    @Override
    public void showLoadErrorDialog() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mHandleMessageReceiver,
                new IntentFilter(Constants.DISPLAY_MESSAGE_ACTION));
        isActive = true;
        Application.activityResumed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActive = false;
        unregisterReceiver(mHandleMessageReceiver);
        Application.activityPaused();
    }

    @Override
    public boolean isActivityActive() {
        return isActive && !isFinishing();
    }

    @Override
    public void showProgress() {
        if (progressDialog != null && isActivityActive()) {
            progressDialog.show();
        }
    }

    @Override
    public void openActivityForResult(Intent intent, int resultCode) {
        startActivityForResult(intent, resultCode);
    }

    @Override
    public void setResultAndCloseActivity(Intent extraIntent) {

    }

    @Override
    public void showErrorDialog(String errorMsg) {
        DialogUtils.showDialog(this, errorMsg, getString(R.string.txt_ok), null);

    }

    @Override
    public void showErrorDialog(int resID) {
        DialogUtils.showDialog(this, getString(resID), getString(R.string.txt_ok), null);
    }

    @Override
    public void showListenerDialog(String errorMsg) {

    }

    @Override
    public void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing() && isActivityActive()) {
            progressDialog.dismiss();
        }
    }

    public void getMessageFromAliens(Bundle bundle) {

    }

    public void onPushReceived(String data, String modiid, String artid) {
        Log.i(TAG, "data" + data);
        Log.i(TAG, "data" + modiid);
        Log.i(TAG, "data" + artid);
        Intent intent =
                new Intent(this, DetailActivity.class);
        intent.putExtra("article_id", artid);
        intent.putExtra("module_id", modiid);
        intent.putExtra("type", "home");
        startActivity(intent);
    }


    private BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            if (!(ButterAppCompatActivity.this instanceof SplashActivity) && !(ButterAppCompatActivity.this instanceof HomeActivity) &&
//                    !(ButterAppCompatActivity.this instanceof LoginActivity)) {
//            onPushReceived(intent.getStringExtra(Constants.MESSAGE_DATA), intent.getStringExtra(Constants.TAG_Article_ID), intent.getStringExtra(Constants.TAG_Module_ID));
//            }
        }
    };
}
