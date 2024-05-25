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
import com.example.bookshelf.data.BookshelfRepository
import com.example.bookshelf.model.Book
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface BookshelfUiState {
    data class Success(val books: Book, val bookThumbnail: List<String>) : BookshelfUiState
    object Error : BookshelfUiState
    object Loading : BookshelfUiState
}

class HomeViewModel(private val bookshelfRepository: BookshelfRepository) : ViewModel() {
    var bookshelfUiState: BookshelfUiState by mutableStateOf(BookshelfUiState.Loading)
        private set

    val thumbnails: List<String> get() = _thumbnails.toList()
    private val _thumbnails: MutableList<String> = mutableListOf()

    init {
        getAllBooks()
    }

    fun getAllBooks() {
        viewModelScope.launch {
            bookshelfUiState = BookshelfUiState.Loading
            bookshelfUiState = try {
                val books = bookshelfRepository.getAllBooks()

                books.items.forEach { book ->
                    val bookDetails = bookshelfRepository.getBookDetails(book.id)
                    val thumbnail = bookDetails.volumeInfo.imageLinks.httpsThumbnail
                    _thumbnails.add(thumbnail)
                    Log.d("list", thumbnail)
                }
                Log.d("UiState.Success", BookshelfUiState.Success(books, thumbnails).toString())
                BookshelfUiState.Success(books, thumbnails)
            } catch (e: IOException) {
                BookshelfUiState.Error
            } catch (e: HttpException) {
                BookshelfUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BookshelfApplication)
                val bookshelfRepository = application.container.bookshelfRepository
                HomeViewModel(bookshelfRepository = bookshelfRepository)
            }
        }
    }
}