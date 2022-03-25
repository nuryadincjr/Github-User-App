package com.nuryadincjr.githubuserapp.util

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.nuryadincjr.githubuserapp.R
import retrofit2.Response

object Constant {
    const val DB_NAME = "users-favorite.db"
    const val DB_VERSION = 1
    const val ARG_SECTION_NUMBER = "section_number"
    const val ARG_LOGIN = "login"
    const val BASE_URL = "https://api.github.com/"
    const val DATA_USER = "data_user"
    const val SPAN_COUNT = 2

    val THEME_KEY = booleanPreferencesKey("theme_setting")

    val TAB_TITLES = intArrayOf(
        R.string.tab_text_1,
        R.string.tab_text_2
    )

    fun <T> responseStatus(response: Response<T>) = when (response.code()) {
        200 -> Event("Response OK, But No Result")
        401 -> Event("${response.code()} : Unauthorized")
        403 -> Event("${response.code()} : Forbidden")
        404 -> Event("${response.code()} : Not Found")
        500 -> Event("${response.code()} : Server Error")
        else -> Event("${response.code()} : ${response.message()}")
    }

    fun throwableStatus(t: Throwable) = when (t.hashCode()) {
        401 -> Event("${t.hashCode()} : Unauthorized")
        403 -> Event("${t.hashCode()} : Forbidden")
        404 -> Event("${t.hashCode()} : Not Found")
        500 -> Event("${t.hashCode()} : Server Error")
        else -> Event("${t.hashCode()} : ${t.message}")
    }

    enum class Color(val value: Int) {
        RED(0xFF0000),
        BLUE_GRAY(0x607D8B)
    }
}