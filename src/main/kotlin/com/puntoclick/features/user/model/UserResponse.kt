package com.puntoclick.features.user.model

import com.puntoclick.data.utils.UUIDSerializer
import com.puntoclick.features.roles.model.RoleResponse
import com.puntoclick.features.team.model.TeamResponse
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class UserResponse(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val name: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val role: RoleResponse,
    val team: TeamResponse,
    val birthday: Long,
)
