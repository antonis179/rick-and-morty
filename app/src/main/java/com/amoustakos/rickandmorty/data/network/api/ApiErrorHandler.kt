package com.amoustakos.rickandmorty.data.network.api

import com.amoustakos.rickandmorty.data.domain.DomainResponse
import retrofit2.Response
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject


interface ApiErrorHandler<R, D> {

    fun hasError(response: Response<*>) = !response.isSuccessful

    fun handleError(response: Response<R>): DomainResponse<D>

    fun handleException(throwable: Throwable): DomainResponse<D> {
        Timber.e(throwable)
        return when (throwable) {
            is UnknownHostException -> DomainResponse.Error.ServerError(
                DomainResponse.Error.ServerError.Reason.SERVER_UNREACHABLE
            )
            else -> DomainResponse.Error.UnknownError(throwable)
        }
    }

    fun handleServerError(response: Response<*>): DomainResponse<D> {
        val reason = when (response.code()) {
            HTTP_CODE_400_BAD_REQUEST -> DomainResponse.Error.ServerError.Reason.BAD_REQUEST
            HTTP_CODE_401_UNAUTHORIZED -> DomainResponse.Error.ServerError.Reason.UNAUTHORIZED
            HTTP_CODE_403_FORBIDDEN -> DomainResponse.Error.ServerError.Reason.FORBIDDEN
            HTTP_CODE_503_SERVICE_UNAVAILABLE -> DomainResponse.Error.ServerError.Reason.SERVICE_UNAVAILABLE
            else -> DomainResponse.Error.ServerError.Reason.UNKNOWN
        }
        return DomainResponse.Error.ServerError(reason = reason)
    }

    companion object {
        private const val HTTP_CODE_400_BAD_REQUEST = 400
        private const val HTTP_CODE_401_UNAUTHORIZED = 401
        private const val HTTP_CODE_403_FORBIDDEN = 403
        private const val HTTP_CODE_503_SERVICE_UNAVAILABLE = 503
    }
}

open class DefaultApiErrorHandlerImpl<R, D> @Inject constructor() : ApiErrorHandler<@JvmSuppressWildcards R, @JvmSuppressWildcards D> {

    override fun handleError(response: Response<R>): DomainResponse<D> {
        return handleServerError(response)
    }

}