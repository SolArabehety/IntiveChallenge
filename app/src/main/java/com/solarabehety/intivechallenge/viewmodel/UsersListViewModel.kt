package com.solarabehety.intivechallenge.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.solarabehety.intivechallenge.model.User
import com.solarabehety.intivechallenge.repository.AppRepository
import io.reactivex.disposables.CompositeDisposable


/**
 * Created by Sol Arabehety on 5/29/2018.
 */
class UsersListViewModel(private val compositeDisposable: CompositeDisposable) : ViewModel() {
    val users: LiveData<List<User>> = AppRepository.getInstance(compositeDisposable).users

    companion object {
        private const val VISIBLE_ITEMS = 6
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_ITEMS >= totalItemCount)
            findUsers()
    }

    fun findUsers() {
        AppRepository.getInstance(compositeDisposable).findUsers()
    }

}