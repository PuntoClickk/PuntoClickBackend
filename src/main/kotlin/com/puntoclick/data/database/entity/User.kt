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
    val password: String,
    val type: Int,
    @Serializable(with = UUIDSerializer::class)
    val role: UUID,
    @Serializable(with = UUIDSerializer::class)
    val team: UUID,
    val validated: Boolean,
    val birthday: Long,
    val createdAt: Long,
    val updateAt: Long
)

