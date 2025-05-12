package com.example.bookshelf.data.model

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(
        val message: String? = null,
        val throwable: Throwable? = null
    ) : Result<Nothing>()
}