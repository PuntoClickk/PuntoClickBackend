package com.puntoclick.data.database.entity

import com.puntoclick.data.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class User(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val name: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val type: Int,
    val role: Role,
    val company: User?,
    val validated: Boolean,
    val birthday: Long,
    val createdAt: Long,
    val lastUpdate: Long
)

