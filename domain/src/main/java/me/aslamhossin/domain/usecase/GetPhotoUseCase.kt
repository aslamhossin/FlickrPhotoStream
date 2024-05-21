package me.aslamhossin.domain.usecase

import me.aslamhossin.domain.model.PhotoItem
import me.aslamhossin.domain.repository.photo.IPhotoRepository
import javax.inject.Inject

class GetPhotoUseCase @Inject constructor(
    private val photoRepository: IPhotoRepository,
) {
    suspend operator fun invoke(): List<PhotoItem> = photoRepository.getPublicPhotos()

}
