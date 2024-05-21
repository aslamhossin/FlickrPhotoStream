package me.aslamhossin.domain.model

data class PhotoItem(
    val title: String,
    val link: String,
    val media: MediaItem,
    val dateTaken: String,
    val description: String,
    val published: String,
    val author: String,
    val authorId: String,
    val tags: String,
)

data class MediaItem(
    val url: String,
)
