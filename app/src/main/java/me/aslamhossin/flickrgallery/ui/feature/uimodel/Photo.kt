package me.aslamhossin.flickrgallery.ui.feature.uimodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import me.aslamhossin.domain.model.PhotoItem

@Parcelize
data class Photo(
    val title: String,
    val link: String,
    val media: Media,
    val dateTaken: String,
    val description: String,
    val published: String,
    val author: String,
    val authorId: String,
    val tags:String
    ) : Parcelable

@Parcelize
data class Media(
    val url: String,
) : Parcelable

fun PhotoItem.toUiModel() = Photo(
    title = this.title,
    link = this.link,
    media = Media(url = this.media.url),
    dateTaken = this.dateTaken,
    description = this.description,
    published = this.published,
    author = this.author,
    authorId = this.authorId,
    tags=tags
)