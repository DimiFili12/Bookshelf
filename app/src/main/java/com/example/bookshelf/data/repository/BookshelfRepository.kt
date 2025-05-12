package com.example.bookshelf.data.repository

import com.example.bookshelf.data.model.Book
import com.example.bookshelf.data.model.BookDetails
import com.example.bookshelf.data.network.BookshelfApiService
import retrofit2.HttpException
import java.io.IOException
import com.example.bookshelf.data.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BookshelfRepository(
    private val bookshelfApiService: BookshelfApiService
) : BookshelfRepositoryInterface {
    override suspend fun getAllBooks(): Result<Book> {
        return withContext(Dispatchers.IO) {
            try {
                val response = bookshelfApiService.getAllBooks("football")
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        Result.Success(body)
                    } else {
                        Result.Error("Empty response body")
                    }
                } else {
                    Result.Error("HTTP error: ${response.code()}")
                }
            } catch (e: Exception) {
                Result.Error(e.message, e)
            }
        }
    }

    override suspend fun getBookDetails(volumeId: String): Result<BookDetails> {
        return withContext(Dispatchers.IO) {
            try {
                val response = bookshelfApiService.getBookDetails(volumeId)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        Result.Success(body)
                    } else {
                        Result.Error("Empty response body")
                    }
                } else {
                    Result.Error("HTTP error: ${response.code()}")
                }
            } catch (e: Exception) {
                Result.Error(e.message, e)
            }
        }
    }
}