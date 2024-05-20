package com.puntoclick.data.model.user

import java.util.UUID

data class UserLogin(
    val id: UUID,
    val password: String,
    val teamUUID: UUID,
    val roleUUID: UUID
)
