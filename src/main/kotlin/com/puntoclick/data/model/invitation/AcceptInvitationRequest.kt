package com.puntoclick.data.model.invitation

import kotlinx.serialization.Serializable

@Serializable
data class AcceptInvitationRequest(
    val email: String,
    val password: String,
    val invitationCode: String
)
