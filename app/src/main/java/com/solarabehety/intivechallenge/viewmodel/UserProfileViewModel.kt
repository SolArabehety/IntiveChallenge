package com.solarabehety.intivechallenge.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.solarabehety.intivechallenge.IntiveApplication
import com.solarabehety.intivechallenge.model.User
import com.solarabehety.intivechallenge.repository.persistence.AppDatabase


/**
 * Created by Sol Arabehety on 6/17/2018.
 */
class UserProfileViewModel(val user: User) : ViewModel(), ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserProfileViewModel::class.java)) {
            return UserProfileViewModel(user) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}