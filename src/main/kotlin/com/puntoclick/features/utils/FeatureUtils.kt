package com.puntoclick.features.utils

import com.puntoclick.data.model.AppResponse
import com.puntoclick.data.model.AppResult
import com.puntoclick.data.model.ErrorResponse
import io.ktor.http.*
import java.util.Locale


fun  <T: Any>AppResult<T>.handleResult() : AppResponse<T> {
    return when(this){
        is AppResult.Success -> AppResponse(data)
        is AppResult.Error -> { AppResponse(error = errorResponse)
        }
    }
}

@Deprecated("use New Method", ReplaceWith("Locale.createGenericError()"))
fun createError(title: String?, description: String?, status: HttpStatusCode = HttpStatusCode.BadRequest): AppResult.Error {
    return AppResult.Error(
        ErrorResponse(
            title =  title ?: "",
            description = description ?: ""
        ),
        appStatus = status
    )
}

fun Locale.createGenericError() = createError(
    StringResourcesKey.GENERIC_TITLE_ERROR_KEY,
    StringResourcesKey.GENERIC_DESCRIPTION_ERROR_KEY, HttpStatusCode.BadRequest)

fun Locale.createError(titleKey: StringResourcesKey? = null, descriptionKey: StringResourcesKey? = null, status: HttpStatusCode = HttpStatusCode.BadRequest): AppResult.Error {
    return AppResult.Error(
        ErrorResponse(
            title =  titleKey?.let { getString(it)  } ?: getString(StringResourcesKey.GENERIC_TITLE_ERROR_KEY),
            description = descriptionKey?.let { getString(it) } ?: getString(StringResourcesKey.GENERIC_DESCRIPTION_ERROR_KEY)
        ),
        appStatus = status
    )
}

