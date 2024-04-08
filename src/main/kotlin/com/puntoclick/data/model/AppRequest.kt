package com.puntoclick.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AppRequest <T>(
    val data: T,
)