package me.aslamhossin.domain.repository.photo

import me.aslamhossin.domain.model.PhotoItem

interface IPhotoRepository {

    suspend fun getPublicPhotos():List<PhotoItem>

}