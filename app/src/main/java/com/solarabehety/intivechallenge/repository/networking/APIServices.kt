package com.solarabehety.intivechallenge.repository.networking

import com.solarabehety.intivechallenge.model.User
import io.reactivex.Single
import retrofit2.http.GET

/**
 * Created by Sol Arabehety on 5/24/2018.
 */
interface APIServices {

    @GET("?inc=picture, name, login, email, id&page=1&results=100&seed=abc")
    fun getUsers(): Single<List<User>>
}