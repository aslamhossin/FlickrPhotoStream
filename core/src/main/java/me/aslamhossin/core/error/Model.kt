package me.aslamhossin.core.error

import me.aslamhossin.core.errorhandling.ApiException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

sealed interface ErrorModel {

    data class ApiError(val exception: ApiException) : ErrorModel

    sealed class Connection : ErrorModel {
        data object Timeout : Connection()
        data object IOError : Connection()
        data object UnknownHost : Connection()
    }

    data class Unknown(val throwable: Throwable) : ErrorModel
}

fun Throwable.toError(): ErrorModel {
    return when (this) {
        is ApiException -> ErrorModel.ApiError(this)
        is SocketTimeoutException -> ErrorModel.Connection.Timeout
        is UnknownHostException -> ErrorModel.Connection.UnknownHost
        is IOException -> ErrorModel.Connection.IOError
        else -> ErrorModel.Unknown(this)
    }
}


val ErrorModel.displayableMessage: String
    get() {
        return when (this) {
            is ErrorModel.Connection -> "Troubles with connection"
            is ErrorModel.ApiError -> exception.displayableMessage
            else -> "Unknown error"
        }
    }
