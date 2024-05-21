package me.aslamhossin.core.errorhandling

import kotlinx.serialization.Serializable


@Serializable
data class ApiErrorDTO(
    val code: Int,
    val message: String,
    val stat: String,
)

fun ApiErrorDTO.toApiError(code: Int) = ApiException(code, message)
