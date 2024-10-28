package com.amoustakos.rickandmorty.data.domain


sealed class DomainResponse<T> {

    class Success<T>(val body: T) : DomainResponse<T>()

    sealed class Error<T> : DomainResponse<T>() {

        data class ServerError<T>(val reason: Reason) : Error<T>() {
            enum class Reason {
                UNAUTHORIZED,
                BAD_REQUEST,
                FORBIDDEN,
                SERVICE_UNAVAILABLE,
                SERVER_UNREACHABLE,
                UNKNOWN
            }
        }

        data class UnknownError<T>(val exception: Throwable? = null) : Error<T>()

        data class TransformationError<T>(val exception: Throwable? = null) : Error<T>()
    }

}
