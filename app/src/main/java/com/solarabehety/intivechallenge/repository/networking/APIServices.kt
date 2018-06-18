package com.solarabehety.intivechallenge.repository.networking

import com.solarabehety.intivechallenge.model.User
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Sol Arabehety on 6/17/2018.
 */
interface APIServices {

    @GET("?inc=picture, name, login, email&seed=abc")
    fun getUsers(@Query("page") page: Int, @Query("results") pageSize: Int): Single<List<User>>
}