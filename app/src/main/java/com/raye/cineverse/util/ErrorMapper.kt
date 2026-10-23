package com.raye.cineverse.util

import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Throwable.toUserFriendlyMessage(): String {
    return when (this) {
        is UnknownHostException, is ConnectException -> {
            "No internet connection. Please check your network and try again."
        }

        is SocketTimeoutException -> {
            "The request timed out. Please try again."
        }

        is HttpException -> {
            when (code()) {
                401, 403 -> "Invalid API key or unauthorized access."
                404 -> "The requested content was not found."
                in 500..599 -> "TMDB servers are temporarily unavailable. Please try again later."
                else -> "Server error (${code()}). Please try again."
            }
        }

        is IOException -> "A network error occured. Please check your connection."
        else -> localizedMessage ?: "An unexpected error occurred."
    }
}
