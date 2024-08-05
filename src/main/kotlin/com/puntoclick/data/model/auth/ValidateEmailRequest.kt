package com.puntoclick.data.model.auth

import kotlinx.serialization.Serializable


@Serializable
data class ValidateEmailRequest(
    val email: String
)
