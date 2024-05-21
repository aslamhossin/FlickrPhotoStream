package me.aslamhossin.data.api.dtos


import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.aslamhossin.domain.model.MediaItem
import me.aslamhossin.domain.model.PhotoItem
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

@Serializable
data class PhotoResponseDto(
    val title: String,
    val link: String,
    val description: String,
    val modified: String,
    val generator: String,
    val items: List<PhotoItemDto>
)

@Serializable
data class PhotoItemDto(
    val title: String,
    val link: String,
    val media: MediaDto,
    @SerialName("date_taken")
    @Contextual
    val dateTaken: DateTime,
    val description: String,
    @Contextual
    val published: DateTime,
    val author: String,
    @SerialName("author_id")
    val authorId: String,
    val tags: String
)

@Serializable
data class MediaDto(
    val m: String
)


private val readableFormatter = DateTimeFormat.forPattern("dd MMM yyyy, HH:mm")

fun PhotoItemDto.toDomain(): PhotoItem = PhotoItem(
    title = title,
    link = link,
    media = media.toDomain(),
    dateTaken = dateTaken.toString(readableFormatter),
    description = description,
    published = published.toString(readableFormatter),
    author = author,
    authorId = authorId,
    tags = tags
)

fun MediaDto.toDomain(): MediaItem = MediaItem(url = m)