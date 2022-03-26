package com.nuryadincjr.githubuserapp.viewModel

import android.content.Context
import com.nuryadincjr.githubuserapp.data.local.room.UsersDatabase
import com.nuryadincjr.githubuserapp.data.remote.response.Users
import com.nuryadincjr.githubuserapp.data.remote.retrofit.ApiConfig
import com.nuryadincjr.githubuserapp.data.repository.UsersRepository
import com.nuryadincjr.githubuserapp.util.factory.ViewModelFactory
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    private val userDummy = "nuryadincjr"
    private lateinit var mainViewModel: MainViewModel
    private lateinit var usersRepository: UsersRepository
    private lateinit var mainActivity: Context
    private lateinit var viewModelFactory: ViewModelFactory

    private val users = Users(
        login = "nuryadincjr",
        id = 51723168,
        nodeId = "MDQ6VXNlcjUxNzIzMTY4",
        avatarUrl = "https://avatars.githubusercontent.com/u/51723168?v=4",
        gravatarId = "",
        url = "https://api.github.com/users/nuryadincjr",
        htmlUrl = "https://github.com/nuryadincjr",
        followersUrl = "https://api.github.com/users/nuryadincjr/followers",
        followingUrl = "https://api.github.com/users/nuryadincjr/following{/other_user}",
        gistsUrl = "https://api.github.com/users/nuryadincjr/gists{/gist_id}",
        starredUrl = "https://api.github.com/users/nuryadincjr/starred{/owner}{/repo}",
        subscriptionsUrl = "https://api.github.com/users/nuryadincjr/subscriptions",
        organizationsUrl = "https://api.github.com/users/nuryadincjr/orgs",
        reposUrl = "https://api.github.com/users/nuryadincjr/repos",
        eventsUrl = "https://api.github.com/users/nuryadincjr/events{/privacy}",
        receivedEventsUrl = "https://api.github.com/users/nuryadincjr/received_events",
        type = "User",
        siteAdmin = false,
        name = "Nuryadin Abutani",
        company = "Sekolah Tinggi Teknologi Bandung",
        blog = "https://nuryadincjr.github.io/",
        location = "indonesia",
        email = null,
        hireable = null,
        bio = "“ Be a Developer not just a User, then Learn and practice\"",
        twitterUsername = "nuryadincjr",
        publicRepos = 62,
        publicGists = 27,
        followers = 2,
        following = 1,
        createdAt = "2019-06-11T20:58:19Z",
        updatedAt = "2022-03-18T14:45:50Z"
    )

    @Before
    fun before() {
        mainActivity = mock(Context::class.java)
        mainViewModel = mock(MainViewModel::class.java)
        usersRepository = mock(UsersRepository::class.java)
        viewModelFactory = mock(ViewModelFactory::class.java)
    }

    @Test
    fun insertFavorite() {
        val apiService = ApiConfig.getApiService()
        val database = UsersDatabase.getInstance(mainActivity)
        val dao = database.usersDao()

        ViewModelFactory.getInstance(mainActivity)
        usersRepository = UsersRepository.getInstance(apiService, dao)
        mainViewModel = MainViewModel(usersRepository)

        mainViewModel.insertFavorite(users)
    }

    @Test
    fun getUsersFavorite() {
        mainViewModel.getUsersFavorite()
    }

    @Test
    fun isUserFavorite() {
        val isFavorite = mainViewModel.isUserFavorite(userDummy)
        assertEquals(true, isFavorite.value)
    }

    @Test
    fun deleteFavorite() {
        mainViewModel.deleteFavorite(userDummy)
    }
}