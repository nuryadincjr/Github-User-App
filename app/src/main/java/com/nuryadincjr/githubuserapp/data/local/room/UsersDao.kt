package com.nuryadincjr.githubuserapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nuryadincjr.githubuserapp.data.local.entity.UsersEntity

@Dao
interface UsersDao {

    @Query("SELECT * FROM favorite where favourite = 1")
    fun getUsersFavorite(): LiveData<List<UsersEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertToFavorite(userList: UsersEntity)

    @Query("DELETE FROM favorite WHERE login = :login")
    fun deleteFromFavorite(login: String)
}