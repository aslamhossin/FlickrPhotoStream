package me.aslamhossin.data.repository.photo

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import me.aslamhossin.core.coroutines.IoDispatcher
import me.aslamhossin.data.api.FlickerApi
import me.aslamhossin.data.api.dtos.toDomain
import me.aslamhossin.domain.model.PhotoItem
import me.aslamhossin.domain.repository.photo.IPhotoRepository
import javax.inject.Inject

class PhotoRepository @Inject constructor(
    private val flickerApi: FlickerApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : IPhotoRepository {

    override suspend fun getPublicPhotos(): List<PhotoItem> = withContext(ioDispatcher) {
        val response = flickerApi.getPublicPhotos()
        val sortedPhotos = response.items.sortedBy { it.dateTaken }
        sortedPhotos.map { it.toDomain() }
    }

}
