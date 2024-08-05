package com.puntoclick.data.model.auth

import com.puntoclick.data.model.user.BaseInfoUser
import kotlinx.serialization.Serializable

@Serializable
data class ValidateEmailResponse(
    val emailExists: Boolean,
    val user: BaseInfoUser? = null,
)
