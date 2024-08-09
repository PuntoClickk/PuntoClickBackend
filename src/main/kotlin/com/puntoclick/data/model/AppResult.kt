package com.puntoclick.data.model

import io.ktor.http.*


sealed class AppResult<out T: Any>(val status: HttpStatusCode) {
    data class Success<T: Any>( val data: T, val appStatus: HttpStatusCode = HttpStatusCode.OK) : AppResult<T>(appStatus)
    data class Error( val errorResponse: ErrorResponse, val appStatus: HttpStatusCode) : AppResult<Nothing>(appStatus)
}