package com.eeyuva.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.eeyuva.Application;
import com.eeyuva.di.component.AppComponent;
import com.eeyuva.utils.customdialog.CustomProgressDialog;

public abstract class BaseFragment extends Fragment implements BaseView  {
    String TAG = BaseFragment.class.getName();

    private boolean isActive = true;

    ProgressDialog progressDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = CustomProgressDialog.getInstance(getActivity());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initalizeFragment(savedInstanceState);
    }

    abstract protected void initalizeFragment(Bundle savedInstanceState);


    protected AppComponent getApplicationComponent() {
        return Application.getAppComponent();
    }


    @Override
    public void showLoadErrorDialog() {

    }

    @Override
    public void onResume() {
        super.onResume();
        isActive = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        isActive = false;
    }

    @Override
    public boolean isActivityActive() {
        return isActive && !getActivity().isFinishing();
    }

    @Override
    public void showProgress() {
        if (progressDialog != null && isActivityActive()) {
            progressDialog.show();
        }
    }

    @Override
    public void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing() && isActivityActive()) {
            progressDialog.dismiss();
        }
    }
}
