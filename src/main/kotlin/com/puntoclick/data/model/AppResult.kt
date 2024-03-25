package com.puntoclick.data.model

import io.ktor.http.*


sealed class AppResult<out T: Any>(val status: HttpStatusCode) {
    data class Success<T: Any>( val data: T, val appStatus: HttpStatusCode) : AppResult<T>(appStatus)
    data class Error( val errorResponse: ErrorResponse, val appStatus: HttpStatusCode) : AppResult<Nothing>(appStatus)
}

fun  <T: Any>AppResult<T>.handleResult() : AppResponse<T>{
    return when(this){
        is AppResult.Success -> AppResponse(data)
        is AppResult.Error -> { AppResponse(error = errorResponse)}
    }
}

fun createError(title: String?, description: String?, status: HttpStatusCode = HttpStatusCode.BadRequest): AppResult.Error {
    return AppResult.Error(
        ErrorResponse(
            title =  title ?: "",
            description = description ?: ""
        ),
        appStatus = status
    )
}
