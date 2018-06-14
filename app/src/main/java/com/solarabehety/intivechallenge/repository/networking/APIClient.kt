package com.solarabehety.intivechallenge.repository.networking

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by Sol Arabehety on 5/24/2018.
 */
class APIClient private constructor() {
    private object Holder {
        val INSTANCE = APIClient()
    }

    companion object {
        val instance: APIClient by lazy { Holder.INSTANCE }
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(APIUsersResponseInterceptor())
            .build()

    val retrofit = Retrofit.Builder()
            .baseUrl("https://randomuser.me/api/")
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

}

