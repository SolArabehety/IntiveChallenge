package com.solarabehety.intivechallenge.repository.persistence

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.solarabehety.intivechallenge.IntiveApplication
import com.solarabehety.intivechallenge.model.User


@Database(entities = [(User::class)], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usersDao(): UsersDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(): AppDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE
                            ?: buildDatabase(IntiveApplication.applicationContext()).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "App.db")
                        .allowMainThreadQueries()
                        .build()
    }
//=


}