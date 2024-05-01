package com.puntoclick.data.model.user

import com.puntoclick.data.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*


@Serializable
data class CreateUserRequest(
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
    val birthday: Long,
)