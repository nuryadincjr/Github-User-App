package com.nuryadincjr.githubuserapp.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class UsersResponse(

    @field:SerializedName("gists_url")
    val gistsUrl: String,

    @field:SerializedName("repos_url")
    val reposUrl: String,

    @field:SerializedName("following_url")
    val followingUrl: String,

    @field:SerializedName("starred_url")
    val starredUrl: String,

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("followers_url")
    val followersUrl: String,

    @field:SerializedName("type")
    val type: String,

    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("subscriptions_url")
    val subscriptionsUrl: String,

    @field:SerializedName("received_events_url")
    val receivedEventsUrl: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("events_url")
    val eventsUrl: String,

    @field:SerializedName("html_url")
    val htmlUrl: String,

    @field:SerializedName("site_admin")
    val siteAdmin: Boolean,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("gravatar_id")
    val gravatarId: String,

    @field:SerializedName("node_id")
    val nodeId: String,

    @field:SerializedName("organizations_url")
    val organizationsUrl: String
)

@Parcelize
data class Users(

    @field:SerializedName("gists_url")
    val gistsUrl: String? = null,

    @field:SerializedName("repos_url")
    val reposUrl: String? = null,

    @field:SerializedName("following_url")
    val followingUrl: String? = null,

    @field:SerializedName("twitter_username")
    val twitterUsername: String? = null,

    @field:SerializedName("bio")
    val bio: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("login")
    var login: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("blog")
    val blog: String? = null,

    @field:SerializedName("subscriptions_url")
    val subscriptionsUrl: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("site_admin")
    val siteAdmin: Boolean? = null,

    @field:SerializedName("company")
    var company: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("public_repos")
    var publicRepos: Int? = null,

    @field:SerializedName("gravatar_id")
    val gravatarId: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("organizations_url")
    val organizationsUrl: String? = null,

    @field:SerializedName("hireable")
    val hireable: String? = null,

    @field:SerializedName("starred_url")
    val starredUrl: String? = null,

    @field:SerializedName("followers_url")
    val followersUrl: String? = null,

    @field:SerializedName("public_gists")
    val publicGists: Int? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("received_events_url")
    val receivedEventsUrl: String? = null,

    @field:SerializedName("followers")
    var followers: Int? = null,

    @field:SerializedName("avatar_url")
    var avatarUrl: String? = null,

    @field:SerializedName("events_url")
    val eventsUrl: String? = null,

    @field:SerializedName("html_url")
    val htmlUrl: String? = null,

    @field:SerializedName("following")
    var following: Int? = null,

    @field:SerializedName("name")
    var name: String? = null,

    @field:SerializedName("location")
    var location: String? = null,

    @field:SerializedName("node_id")
    val nodeId: String? = null
) : Parcelable