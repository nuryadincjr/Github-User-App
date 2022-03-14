package com.nuryadincjr.githubuserapp.util

import com.nuryadincjr.githubuserapp.R
import retrofit2.Response

object Constant {
    const val ARG_SECTION_NUMBER = "section_number"
    const val ARG_LOGIN = "login"
    const val BASE_URL = "https://api.github.com/"
    const val DELAY_MILLS = 3000L
    const val DATA_USER = "data_user"
    const val TOKEN = "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"
    const val SPAN_COUNT = 2

    val TAB_TITLES = intArrayOf(
        R.string.tab_text_1,
        R.string.tab_text_2
    )

    fun <T> responseStatus(response: Response<T>) = when (response.hashCode()) {
        401 -> Event("$response : Unauthorized")
        403 -> Event("$response : Forbidden")
        404 -> Event("$response : Not Found")
        500 -> Event("$response : Server Error")
        else -> Event("$response : ${response.message()}")
    }

    fun throwableStatus(t: Throwable) = when (t.hashCode()) {
        401 -> Event("${t.hashCode()} : Unauthorized")
        403 -> Event("${t.hashCode()} : Forbidden")
        404 -> Event("${t.hashCode()} : Not Found")
        500 -> Event("${t.hashCode()} : Server Error")
        else -> Event("${t.hashCode()} : ${t.message}")
    }
}