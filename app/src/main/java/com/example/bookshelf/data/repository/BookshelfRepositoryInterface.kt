package com.example.bookshelf.data.repository

import com.example.bookshelf.data.model.Book
import com.example.bookshelf.data.model.BookDetails
import com.example.bookshelf.data.network.BookshelfApiService
import com.example.bookshelf.data.model.Result

interface BookshelfRepositoryInterface {
    suspend fun getAllBooks(): Result<Book>

    suspend fun getBookDetails(volumeId: String): Result<BookDetails>
}