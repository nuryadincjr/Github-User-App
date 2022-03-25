package com.nuryadincjr.githubuserapp.viewModel

import android.content.Context
import com.nuryadincjr.githubuserapp.data.local.room.UsersDatabase
import com.nuryadincjr.githubuserapp.data.remote.retrofit.ApiConfig
import com.nuryadincjr.githubuserapp.data.repository.UsersRepository
import com.nuryadincjr.githubuserapp.ui.MainActivity
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    private val userDummy = "ivey"
    private lateinit var mainViewModel: MainViewModel
    private lateinit var usersRepository: UsersRepository

    @Mock
    private lateinit var mockContext: Context

    @Before
    fun before() {
        mockContext = mock(MainActivity::class.java)
        usersRepository = mock(UsersRepository::class.java)
        mainViewModel = MainViewModel(usersRepository)

    }

    @Test
    fun getUsersFavorite() {

    }

    @Test
    fun isUserFavorite() {
        val apiService = ApiConfig.getApiService()
        val database = UsersDatabase.getInstance(mockContext)
        val dao = database.usersDao()

        usersRepository =  UsersRepository.getInstance(apiService, dao)
        mainViewModel = MainViewModel(usersRepository)

        val isFavorite = mainViewModel.isUserFavorite(userDummy).value
        assertEquals(true, isFavorite)
    }


    @Test
    fun insertFavorite() {
    }

    @Test
    fun deleteFavorite() {
    }
}