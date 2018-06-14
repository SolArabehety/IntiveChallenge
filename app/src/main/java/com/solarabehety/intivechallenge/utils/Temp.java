package com.solarabehety.intivechallenge.utils;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

import com.solarabehety.intivechallenge.R;
import com.solarabehety.intivechallenge.repository.networking.APIUsersResponseInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sol Arabehety on 6/12/2018.
 */
public class Temp {
    public void func() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new APIUsersResponseInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://randomuser.me/api/")
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
