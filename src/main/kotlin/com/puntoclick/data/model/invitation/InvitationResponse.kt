package com.puntoclick.data.model.invitation

import kotlinx.serialization.Serializable

@Serializable
data class InvitationResponse(
    val code: String,
    val expiresAt: Long
)
