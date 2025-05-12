package com.example.bookshelf.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf.BookshelfApplication
import com.example.bookshelf.data.repository.BookshelfRepositoryInterface
import com.example.bookshelf.data.model.Book
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import com.example.bookshelf.data.model.Result

sealed interface BookshelfUiState {
    data class Success(val books: Book, val bookThumbnail: List<String>) : BookshelfUiState
    object Error : BookshelfUiState
    object Loading : BookshelfUiState
}

class HomeViewModel(private val bookshelfRepositoryInterface: BookshelfRepositoryInterface) : ViewModel() {
    var bookshelfUiState: BookshelfUiState by mutableStateOf(BookshelfUiState.Loading)
        private set

    private var _thumbnails: MutableList<String> = mutableListOf()
    val thumbnails: List<String> get() = _thumbnails.toList()

    init {
        getAllBooks()
    }


    fun getAllBooks() {
        viewModelScope.launch {
            val booksResult = bookshelfRepositoryInterface.getAllBooks()
            when (booksResult) {
                is Result.Error -> bookshelfUiState = BookshelfUiState.Error
                is Result.Success -> {
                    booksResult.data.items.forEach { book ->
                        val bookDetails = bookshelfRepositoryInterface.getBookDetails(book.id)
                        when (bookDetails) {
                            is Result.Error -> bookshelfUiState = BookshelfUiState.Error
                            is Result.Success -> {
                                val thumbnail = bookDetails.data.volumeInfo.imageLinks.httpsThumbnail
                                _thumbnails.add(thumbnail)
                            }
                        }
                    }
                    bookshelfUiState = BookshelfUiState.Success(booksResult.data, thumbnails)
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BookshelfApplication)
                val bookshelfRepository = application.container.bookshelfRepositoryInterface
                HomeViewModel(bookshelfRepositoryInterface = bookshelfRepository)
            }
        }
    }
}