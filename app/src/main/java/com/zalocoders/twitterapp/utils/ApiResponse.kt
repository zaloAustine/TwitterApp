package com.zalocoders.twitterapp.utils

sealed class ApiResponse<out T> {
	data class Success<out T>(val value: T) : ApiResponse<T>()
	data class Failure(val errorHolder: ErrorHolder) : ApiResponse<Nothing>()
}

data class ErrorHolder(override val message: String, val statusCode: Int?) : Exception(message)
