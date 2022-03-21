package com.nuryadincjr.githubuserapp.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "favorite")
class UsersEntity(
    @field:ColumnInfo(name = "login")
    @field:PrimaryKey
    val login: String,

    @field:ColumnInfo(name = "name")
    val name: String,

    @field:ColumnInfo(name = "avatar_url")
    val avatarUrl: String? = null,

    @field:ColumnInfo(name = "followers")
    val followers: String? = null,

    @field:ColumnInfo(name = "following")
    val following: String? = null,

    @field:ColumnInfo(name = "company")
    val company: String? = null,

    @field:ColumnInfo(name = "location")
    val location: String? = null,

    @field:ColumnInfo(name = "public_repos")
    val publicRepos: String? = null,

    @field:ColumnInfo(name = "favourite")
    var isFavourite: Boolean
) : Parcelable