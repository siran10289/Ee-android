package com.eeyuva.base;

import android.content.Intent;

/**
 * Created by hari on 10/7/16.
 */
public interface BaseView {
    void showLoadErrorDialog();

    boolean isActivityActive();

    void hideProgress();

    void showProgress();

    void openActivityForResult(Intent intent, int resultCode);

    void setResultAndCloseActivity(Intent extraIntent);

    void showErrorDialog(String errorMsg);

    void showErrorDialog(int resID);

    void showListenerDialog(String errorMsg);

}
