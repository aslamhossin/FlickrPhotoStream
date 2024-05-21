package me.aslamhossin.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.aslamhossin.data.repository.file.FileRepository
import me.aslamhossin.data.repository.photo.PhotoRepository
import me.aslamhossin.domain.repository.file.IFileRepository
import me.aslamhossin.domain.repository.photo.IPhotoRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPhotoRepository(photoRepository: PhotoRepository): IPhotoRepository

    @Binds
    @Singleton
    abstract fun bindFileRepository(fileRepository: FileRepository): IFileRepository

}
