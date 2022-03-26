package com.nuryadincjr.githubuserapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nuryadincjr.githubuserapp.data.local.entity.UsersEntity

@Dao
interface UsersDao {

    @Query("SELECT * FROM user ORDER BY name")
    fun getUsersFavorite(): LiveData<List<UsersEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(user: UsersEntity)

    @Query("DELETE FROM user WHERE login = :login")
    suspend fun deleteFavorite(login: String)

    @Query("SELECT EXISTS(SELECT * FROM user WHERE login = :login)")
    suspend fun isFavorite(login: String): Boolean
}