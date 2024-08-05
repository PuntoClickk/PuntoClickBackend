package com.puntoclick.data.model.invitation

import java.util.UUID

data class InvitationData(
    val code: String,
    val expiresAt: Long,
    val teamId: UUID
)
