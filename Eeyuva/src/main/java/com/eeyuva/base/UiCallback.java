package com.eeyuva.base;


import android.util.Log;

import com.eeyuva.BuildConfig;
import com.eeyuva.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.net.HttpURLConnection.HTTP_OK;

public class UiCallback<T> implements Callback<T> {
    private Response<T> response;
    private T responseBody;
    private BaseView baseView;
    private Call<T> requestCall;
    private LoadListener<T> loadListener;

    private boolean withProgress = true;

    public UiCallback(BaseView baseView, LoadListener<T> loadListener, boolean state) {
        this.baseView = baseView;
        this.loadListener = loadListener;
        this.withProgress = state;
    }

    public void start(Call<T> call) {
        requestCall = call;
        showProgress();
        requestCall.enqueue(this);
    }

    private void showProgress() {
        if (baseView != null && withProgress) {
            baseView.showProgress();
        }
    }

    private void hideProgress() {
        if (baseView != null && withProgress) {
            baseView.hideProgress();
        }
    }

    private boolean isResponseCodeOk(Response<T> response) {
        return HTTP_OK == response.code() || HttpURLConnection.HTTP_CREATED == response.code();
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        Log.i("onResponse: ", "" + requestCall.request().url().toString() + " " +
                response.message() + " code " + response.code());
        try {
        if (loadListener != null) {
            if (response.isSuccessful()) {
                this.response = response;
                responseBody = response.body();
                hideProgress();
                loadListener.onSuccess(responseBody);
            } else {

                    Object error = null;
                    if (requestCall.request().url().toString().contains(BuildConfig.BASE_URL))
                        error = GsonConverterFactory.create().responseBodyConverter(ApiError.class, ApiError.class.getAnnotations(), null).convert(response.errorBody());
                    else
                        error = GsonConverterFactory.create().responseBodyConverter(Object.class, Object.class.getAnnotations(), null).convert(response.errorBody());

                    loadListener.onError(error);

                if (baseView != null && withProgress) {
                    baseView.hideProgress();
                    baseView.showLoadErrorDialog();
                }
            }
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.i("onFailure", "onFailure: " + requestCall.request().url().toString());
        Log.i("onFailure", "onFailure: " + " " + t.getLocalizedMessage());
        Log.e("Error:", t.getLocalizedMessage());
        t.printStackTrace();

        if (loadListener != null) {
            if (baseView == null) {
                loadListener.onFailure(t);
            } else if (baseView.isActivityActive()) {
                loadListener.onFailure(t);
                if (t instanceof TimeoutException || t instanceof SocketTimeoutException)
                    baseView.showErrorDialog(R.string.timeout_error);
                else if (t instanceof UnknownHostException)
                    baseView.showErrorDialog(R.string.no_network_connection_error);
                else if(t instanceof NoRouteToHostException || t instanceof UnknownHostException)
                    baseView.showErrorDialog(R.string.server_error);
                else
                    baseView.showErrorDialog(R.string.generic_error);
                if (withProgress) {
                    baseView.hideProgress();
                }
            }
        }
    }

}