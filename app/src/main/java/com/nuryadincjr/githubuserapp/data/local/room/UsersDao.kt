package com.nuryadincjr.githubuserapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nuryadincjr.githubuserapp.data.local.entity.UsersEntity

@Dao
interface UsersDao {

    @Query("SELECT * FROM user where favourite = 1")
    fun getUsersFavorite(): LiveData<List<UsersEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertToFavorite(userList: UsersEntity)

    @Query("DELETE FROM user WHERE login = :login")
    fun deleteFromFavorite(login: String)
//


    @Query("SELECT * FROM user ORDER BY name DESC")
    fun getNews(): LiveData<List<UsersEntity>>

    @Query("SELECT * FROM user where favourite = 1")
    fun getBookmarkedNews(): LiveData<List<UsersEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNews(news: List<UsersEntity>)

    @Update
    fun updateNews(news: UsersEntity)

    @Query("DELETE FROM user WHERE favourite = 0")
    fun deleteAll()

    @Query("DELETE FROM user")
    fun deleteAlla()

    @Query("SELECT EXISTS(SELECT * FROM user WHERE login = :login AND favourite = 1)")
    fun isNewsBookmarked(login: String): Boolean

    @Update
    suspend fun updateUser(news: UsersEntity)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteUser(user: UsersEntity)

    @Delete
    suspend fun deleteFavoriteUser(user: UsersEntity)
}