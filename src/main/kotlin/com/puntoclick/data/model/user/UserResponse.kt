package com.puntoclick.data.model.user

import com.puntoclick.data.utils.UUIDSerializer
import com.puntoclick.data.model.role.RoleResponse
import com.puntoclick.data.model.team.TeamResponse
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
