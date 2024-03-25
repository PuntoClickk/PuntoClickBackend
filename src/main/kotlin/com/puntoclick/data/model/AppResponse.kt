package com.puntoclick.data.model

import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
data class AppResponse<T>(
    val data: T? = null,
    val status: Int = HttpStatusCode.OK.value,
    val error: ErrorResponse? = null
)