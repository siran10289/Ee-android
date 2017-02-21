package com.eeyuva.base;

/**
 * Created by hari on 13/7/16.
 */
public interface LoadListener<T> {
    void onSuccess(T responseBody);

    void onFailure(Throwable t);

    void onError(Object error);


}
