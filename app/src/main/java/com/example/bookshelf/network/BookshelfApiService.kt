package com.example.bookshelf.network

import com.example.bookshelf.model.Book
import com.example.bookshelf.model.BookDetails
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookshelfApiService {
    @GET("volumes")
    suspend fun getAllBooks(@Query("q") query: String): Book

    @GET("volumes/{volumeId}")
    suspend fun getBookDetails(@Path("volumeId") volumeId: String): BookDetails
}