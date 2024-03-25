package com.puntoclick.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val title: String,
    val description: String
)