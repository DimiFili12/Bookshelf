package com.example.bookshelf.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookDetails (
    val volumeInfo: VolumeInfo
)

@Serializable
data class VolumeInfo(
    val imageLinks: ImageLinks = ImageLinks()
)

@Serializable
data class ImageLinks(
    @SerialName(value = "thumbnail")
    val imgSrc: String = ""
){
    val httpsThumbnail: String
        get() = if (imgSrc.isNotEmpty()) {
            imgSrc.replace("http://", "https://")
        } else {
            imgSrc
        }
}