package com.example.bookshelf.data

import com.example.bookshelf.data.network.BookshelfApiService
import com.example.bookshelf.data.repository.BookshelfRepositoryInterface
import com.example.bookshelf.data.repository.BookshelfRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val bookshelfRepositoryInterface: BookshelfRepositoryInterface
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://www.googleapis.com/books/v1/"

    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(
            json.asConverterFactory("application/json".toMediaType())
        )
        .build()

    private val retrofitService: BookshelfApiService by lazy {
        retrofit.create(BookshelfApiService::class.java)
    }

    override val bookshelfRepositoryInterface: BookshelfRepositoryInterface by lazy {
        BookshelfRepository(retrofitService)
    }
}