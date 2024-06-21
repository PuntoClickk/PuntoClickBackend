package com.puntoclick.data.model.invitation

import java.util.UUID


data class CreateInvitationData(
    val code: String,
    val expiresAt: Long,
    val teamId: UUID
)
