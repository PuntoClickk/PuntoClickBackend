package com.puntoclick.data.model.invitation

import kotlinx.serialization.Serializable


@Serializable
data class AcceptInvitationResponse(
    val userAdded: Boolean
)
