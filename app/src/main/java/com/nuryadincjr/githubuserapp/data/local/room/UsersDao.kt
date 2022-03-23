package com.nuryadincjr.githubuserapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nuryadincjr.githubuserapp.data.local.entity.UsersEntity
import com.nuryadincjr.githubuserapp.util.Event

@Dao
interface UsersDao {

    @Query("SELECT * FROM user where favourite = 1")
    fun getUsersFavorite(): LiveData<List<UsersEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(user: UsersEntity)

    @Query("DELETE FROM user WHERE login = :login")
    suspend fun deleteFavorite(login: String)


    @Query("SELECT EXISTS(SELECT * FROM user WHERE login = :login)")
    suspend fun isFavorite(login: String): Boolean
}