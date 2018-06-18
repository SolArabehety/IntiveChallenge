package com.solarabehety.intivechallenge.repository.persistence

import android.arch.persistence.room.*
import com.solarabehety.intivechallenge.model.User
import io.reactivex.Single


/**
 * Created by Sol Arabehety on 6/17/2018.
 */
@Dao
interface UsersDao {

    @Query("SELECT * FROM User  LIMIT :page, :pageSize")
    fun findUsers(page : Int, pageSize : Int): Single<List<User>>

    @Query("SELECT COUNT(*) FROM User")
    fun getUsersQuantity(): Int

    @Query("SELECT * FROM User WHERE id = :userId")
    fun findUserById(userId: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(users: List<User>)


}