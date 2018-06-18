package com.solarabehety.intivechallenge.repository

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.solarabehety.intivechallenge.IntiveApplication
import com.solarabehety.intivechallenge.model.User
import com.solarabehety.intivechallenge.repository.networking.APIClient
import com.solarabehety.intivechallenge.repository.networking.APIServices
import com.solarabehety.intivechallenge.repository.persistence.AppDatabase
import com.solarabehety.intivechallenge.ui.activities.MainActivity
import com.solarabehety.intivechallenge.utils.SingletonHolder
import com.solarabehety.intivechallenge.utils.Utils
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

/**
 * Created by Sol Arabehety on 6/13/2018.
 */
class AppRepository private constructor(private val compositeDisposable: CompositeDisposable) {
    companion object : SingletonHolder<AppRepository, CompositeDisposable>(::AppRepository)


    private val pageSize = 20
    private var currentDBPage = 0
    private var currentNetworkPage = 1 // to contemplate network errors
    private var isRequestInProgress = false
    val users: MutableLiveData<List<User>> = MutableLiveData()


    fun findUsers() {

        if (currentDBPage == 0 || AppDatabase.getInstance().usersDao().getUsersQuantity() <= currentDBPage * pageSize)
            getUsersFromServer()
        else if (currentDBPage != 0)
            findUsersInDB()
    }

    private fun findUsersInDB() {
        val dbObserver = AppDatabase.getInstance().usersDao().findUsers(currentDBPage * pageSize, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        compositeDisposable.add(dbObserver.subscribeWith(object : DisposableSingleObserver<List<User>>() {
            override fun onSuccess(_users: List<User>) {
                Log.i("AppRepository_BD", "Saving users on DB page $currentDBPage")
                val mutableList = mutableListOf<User>()
                users.value?.let { mutableList.addAll(it) }
                if (_users.isNotEmpty()) {
                    currentDBPage++
                    mutableList.addAll(_users)
                    users.postValue(mutableList)
                }
            }

            override fun onError(e: Throwable) {
                Log.e("AppRepository", "Unable to clear database", e.cause)
            }
        }))
    }

    private fun getUsersFromServer() {
        if (!Utils.checkInternetConnection(IntiveApplication.applicationContext()))
            return

        if (isRequestInProgress)
            return

        isRequestInProgress = true

        val apiService = APIClient.instance.retrofit.create(APIServices::class.java)
        val serverObserver = apiService.getUsers(currentNetworkPage, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        Log.i("AppRepository_NETWORK", "Looking for users on page $currentNetworkPage")
        compositeDisposable.add(serverObserver.subscribeWith(object : DisposableSingleObserver<List<User>>() {
            override fun onSuccess(users: List<User>) {
                isRequestInProgress = false
                currentNetworkPage++
                Log.i("AppRepository_NETWORK", "Server success")
                saveUsersInDB(users)
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                isRequestInProgress = false

                // Strategy for server errors: skip the current page
                currentNetworkPage++
                findUsers()
            }
        }))
    }

    private fun saveUsersInDB(users: List<User>) {
        val completable =
                Completable.fromAction { AppDatabase.getInstance().usersDao().insertUsers(users) }

        compositeDisposable.add(completable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.i("AppRepository_BD", "users saved on DB")
                    findUsersInDB()
                }, { error -> Log.e("AppRepository", "Unable to save users", error) }))
    }


}