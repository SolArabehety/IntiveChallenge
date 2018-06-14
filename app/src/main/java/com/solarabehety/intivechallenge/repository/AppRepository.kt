package com.solarabehety.intivechallenge.repository

import android.util.Log
import com.solarabehety.intivechallenge.BuildConfig
import com.solarabehety.intivechallenge.IntiveApplication
import com.solarabehety.intivechallenge.model.User
import com.solarabehety.intivechallenge.repository.networking.APIClient
import com.solarabehety.intivechallenge.repository.networking.APIServices
import com.solarabehety.intivechallenge.repository.networking.UsersCallback
import com.solarabehety.intivechallenge.repository.persistence.AppDatabase
import com.solarabehety.intivechallenge.utils.Constants
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

/**
 * Created by Sol Arabehety on 6/13/2018.
 */
class AppRepository private constructor() {
    private val compositeDisposable = CompositeDisposable()

    private object Holder {
        val INSTANCE = AppRepository()
    }

    companion object {
        val instance: AppRepository by lazy { Holder.INSTANCE }
    }

    fun getUsers(callback: UsersCallback): CompositeDisposable {
        val apiService = APIClient.instance.retrofit.create(APIServices::class.java)
        val serverObserver = apiService.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        compositeDisposable.add(serverObserver.subscribeWith(object : DisposableSingleObserver<List<User>>() {
            override fun onSuccess(users: List<User>) {
                Log.i(AppRepository::class.java.simpleName, "Server success")
                saveUsersInDB(users, callback)
            }

            override fun onError(e: Throwable) {
                Log.e(AppRepository::class.java.simpleName, "Server Error" + e.message)
                val users = AppDatabase.getInstance(IntiveApplication.applicationContext()).workOrdersDao().findUsers()
                if (users.value != null && users.value!!.isNotEmpty())
                    callback.onSuccess()
                else if (BuildConfig.DEBUG && e.message != null)
                    callback.onError(e.message!!)
                else
                    callback.onError(Constants.SERVER_ERROR)
            }
        }))

        return compositeDisposable
    }

    private fun saveUsersInDB(users: List<User>, callback: UsersCallback) {
        Single.fromCallable { AppDatabase.getInstance(IntiveApplication.applicationContext()).workOrdersDao().insertUsers(users) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : SingleObserver<Any> {
                    override fun onSuccess(t: Any) {
                        callback.onSuccess()
                    }

                    override fun onError(e: Throwable) {
                        callback.onSuccess()
                    }

                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable.add(d)
                    }

                })
    }


}