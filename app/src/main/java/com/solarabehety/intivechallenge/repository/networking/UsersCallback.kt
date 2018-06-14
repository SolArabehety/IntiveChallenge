package com.solarabehety.intivechallenge.repository.networking

/**
 * Created by Sol Arabehety on 6/13/2018.
 */
interface UsersCallback {
    fun onSuccess()
    fun onError(error: String)
}