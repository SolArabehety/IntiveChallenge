package com.solarabehety.intivechallenge.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.solarabehety.intivechallenge.IntiveApplication
import com.solarabehety.intivechallenge.model.User
import com.solarabehety.intivechallenge.repository.persistence.AppDatabase


/**
 * Created by Sol Arabehety on 5/29/2018.
 */
class ProfileListViewModel : ViewModel() {
    val users: LiveData<List<User>> = AppDatabase.getInstance(IntiveApplication.applicationContext()).workOrdersDao().findUsers()


}