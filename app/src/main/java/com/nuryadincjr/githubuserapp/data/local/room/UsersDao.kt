package com.nuryadincjr.githubuserapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nuryadincjr.githubuserapp.data.local.entity.UsersEntity

@Dao
interface UsersDao {
    @Query("SELECT * FROM favorite ORDER BY name DESC")
    fun getUsers(): LiveData<List<UsersEntity>>

    @Query("SELECT * FROM favorite where favourite = 1")
    fun getUsersFavorite(): LiveData<List<UsersEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(news: List<UsersEntity>)

    @Update
    suspend fun updateUser(news: UsersEntity)

    @Query("DELETE FROM favorite WHERE favourite = 0")
    fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM favorite WHERE login = :login AND favourite = 1)")
    fun isUserFavorite(login: String): Boolean
}