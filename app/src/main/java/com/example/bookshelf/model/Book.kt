package com.example.bookshelf.model

import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val items: List<Items>
)

@Serializable
data class Items(
    val id: String,
)
