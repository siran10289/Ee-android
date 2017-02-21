package com.eeyuva.base;

import android.content.Intent;

/**
 * Created by hari on 10/7/16.
 */
public interface BasePresenter {
    void setView(BaseView view);

    void onActivityResult(int requestCode, int resultCode, Intent data);
}
