package me.aslamhossin.data.api

import me.aslamhossin.data.api.dtos.PhotoResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickerApi {

    @GET("photos_public.gne")
    suspend fun getPublicPhotos(
        @Query("format") format: String = "json",
        @Query("nojsoncallback") nojsoncallback: Int = 1,
        @Query("tags") tags: String? = null
    ): PhotoResponseDto

}