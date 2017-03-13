package com.eeyuva.di.module;

import android.content.Context;
import android.support.annotation.NonNull;

import com.eeyuva.BuildConfig;
import com.eeyuva.apiservice.Api;
import com.eeyuva.apiservice.RequestHeaderInterceptor;
import com.eeyuva.di.scope.GsonRestAdapter;
import com.eeyuva.utils.preferences.PrefsManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class NetworkModule {

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(Context context, PrefsManager prefsManager) {

   /*     ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .allEnabledTlsVersions()
                .allEnabledCipherSuites()
                .build();
*/

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(new RequestHeaderInterceptor(prefsManager));
        setLoggingInterceptor(clientBuilder);
       // clientBuilder.connectionSpecs(Collections.singletonList(spec));
        clientBuilder.cache(getHttpCache(context));
        setTimeouts(clientBuilder);
        return clientBuilder.build();
    }

    private void setTimeouts(OkHttpClient.Builder clientBuilder) {
        clientBuilder.connectTimeout(60 * 1000, TimeUnit.MILLISECONDS);
        clientBuilder.readTimeout(60 * 1000, TimeUnit.MILLISECONDS);
    }

    private void setLoggingInterceptor(OkHttpClient.Builder clientBuilder) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder.addInterceptor(logging);
    }

    @NonNull
    private Cache getHttpCache(Context context) {
        final File httpCacheDir = new File(context.getCacheDir(), "http");
        final long httpCacheSize = 10 * 1024 * 1024; // 10 MiB
        return new Cache(httpCacheDir, httpCacheSize);
    }

    @Provides
    @Named("baseUrl")
    public String provideCustomBaseUrl() {
        return BuildConfig.BASE_URL;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    @GsonRestAdapter
    public Retrofit provideGsonRestAdapter(OkHttpClient okHttpClient) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return new Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    @GsonRestAdapter
    Api provideGsonApi(@GsonRestAdapter Retrofit retrofit) {
        return retrofit.create(Api.class);
    }


    @Provides
    @Singleton
    Api provideApi(Retrofit retrofit) {
        return retrofit.create(Api.class);
    }

}
