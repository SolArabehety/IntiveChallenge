package com.solarabehety.intivechallenge.repository.persistence

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.solarabehety.intivechallenge.model.User

/**
 * Created by Sol Arabehety on 5/28/2018.
 */
@Dao
interface UsersDao {

    @Query("SELECT * FROM User")
    fun findUsers(): LiveData<List<User>>


    @Query("SELECT * FROM User WHERE id = :userId")
    fun findUserById(userId: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(users: List<User>)


}