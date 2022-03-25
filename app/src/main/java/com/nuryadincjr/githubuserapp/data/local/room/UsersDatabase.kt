package com.nuryadincjr.githubuserapp.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nuryadincjr.githubuserapp.data.local.entity.UsersEntity
import com.nuryadincjr.githubuserapp.util.Constant.DB_NAME
import com.nuryadincjr.githubuserapp.util.Constant.DB_VERSION

@Database(entities = [UsersEntity::class], version = DB_VERSION, exportSchema = false)
abstract class UsersDatabase : RoomDatabase() {
    abstract fun usersDao(): UsersDao

    companion object {
        @Volatile
        private var instance: UsersDatabase? = null
        fun getInstance(context: Context): UsersDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    UsersDatabase::class.java, DB_NAME
                ).build()
            }
    }
}