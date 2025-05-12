package com.example.bookshelf.data.network

import com.example.bookshelf.data.model.Book
import com.example.bookshelf.data.model.BookDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookshelfApiService {
    @GET("volumes")
    suspend fun getAllBooks(@Query("q") query: String): Response<Book>

    @GET("volumes/{volumeId}")
    suspend fun getBookDetails(@Path("volumeId") volumeId: String): Response<BookDetails>
}