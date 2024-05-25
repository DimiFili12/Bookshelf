package com.example.bookshelf.data

import com.example.bookshelf.model.Book
import com.example.bookshelf.model.BookDetails
import com.example.bookshelf.network.BookshelfApiService

interface BookshelfRepository {
    suspend fun getAllBooks(): Book

    suspend fun getBookDetails(volumeId: String): BookDetails
}

class NetworkBookshelfRepository(
    private val bookshelfApiService: BookshelfApiService
) : BookshelfRepository {
    override suspend fun getAllBooks(): Book = bookshelfApiService.getAllBooks("football")

    override suspend fun getBookDetails(volumeId: String): BookDetails = bookshelfApiService.getBookDetails(volumeId)
}