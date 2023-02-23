package com.sultonbek1547.rxkotlin.database

import androidx.room.*
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface MyUserDao {

    @Query("select * from user")
    fun getAllUser(): Flowable<List<User>>

    @Insert
    fun addUser(user: User): Single<Long>

    @Delete
    fun deleteUser(user: User)

}