package com.puntoclick.features.utils

import com.puntoclick.data.model.AppResponse
import com.puntoclick.data.model.AppResult
import com.puntoclick.data.model.ErrorResponse
import io.ktor.http.*


fun  <T: Any>AppResult<T>.handleResult() : AppResponse<T> {
    return when(this){
        is AppResult.Success -> AppResponse(data)
        is AppResult.Error -> { AppResponse(error = errorResponse)
        }
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


inline fun <T: Any> tryCatch( block: () -> AppResult<T>): AppResult<T> {
    return try {
        block.invoke()
    } catch (e: Exception) {
        createError(e.message, e.message)
    }
}
