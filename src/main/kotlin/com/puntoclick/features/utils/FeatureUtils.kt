package com.puntoclick.features.utils

import com.puntoclick.data.model.AppResponse
import com.puntoclick.data.model.AppResult
import com.puntoclick.data.model.ErrorResponse
import io.ktor.http.*


fun <T : Any> AppResult<T>.handleResult(): AppResponse<T> {
    return when (this) {
        is AppResult.Success -> AppResponse(data)
        is AppResult.Error -> {
            AppResponse(error = errorResponse)
        }
    }
}

fun createGenericError() = createError(
    StringResourcesKey.GENERIC_TITLE_ERROR_KEY,
    StringResourcesKey.GENERIC_DESCRIPTION_ERROR_KEY, HttpStatusCode.BadRequest
)

fun createError(
    titleKey: StringResourcesKey? = null,
    descriptionKey: StringResourcesKey? = null,
    status: HttpStatusCode = HttpStatusCode.BadRequest
): AppResult.Error {
    return AppResult.Error(
        ErrorResponse(
            title = titleKey?.getString()
                ?: StringResourcesKey.GENERIC_TITLE_ERROR_KEY.getString(),
            description = descriptionKey?.getString()
                ?: StringResourcesKey.GENERIC_DESCRIPTION_ERROR_KEY.getString()
        ),
        appStatus = status
    )
}



