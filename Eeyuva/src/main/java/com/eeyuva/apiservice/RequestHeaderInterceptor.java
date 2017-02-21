package com.eeyuva.apiservice;

import com.eeyuva.BuildConfig;
import com.eeyuva.utils.preferences.PrefsManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hari on 22/6/16.
 */
public class RequestHeaderInterceptor implements Interceptor {

    private PrefsManager prefsManager;

    public RequestHeaderInterceptor(PrefsManager prefsManager) {
        this.prefsManager = prefsManager;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        String accessToken = prefsManager.getAccessToken();

        Request original = chain.request();
        String url = original.url().url().toString();

        Request.Builder builder = original.newBuilder()
                .header("Accept-Language", "en-US");

//        if (!url.containsins(BuildConfig.S3_URL))
            builder.addHeader("Content-type", "application/json");

//        if (accessToken != null && accessToken.length() != 0)
//            if (url.contains(BuildConfig.PORT_8001))
//                builder.header("OMSTOKEN", accessToken);
//            else if (!original.url().url().toString().contains(BuildConfig.MW_BASE_URL) && !original.url().url().toString().contains(BuildConfig.S3_URL))
//                builder.header("Authorization", "Token " + accessToken);

        Request request =
                builder.method(original.method(), original.body())
                        .build();
        return chain.proceed(request);
    }


}
