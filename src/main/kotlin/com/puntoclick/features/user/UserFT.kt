package com.puntoclick.features.user

import com.puntoclick.data.database.entity.Role
import com.puntoclick.data.database.entity.Team
import com.puntoclick.data.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

data class UserFT(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val name: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val type: Int,
    val role: Role,
    val team: Team,
    val validated: Boolean,
    val birthday: Long,
    val createdAt: Long,
    val updateAt: Long
)
