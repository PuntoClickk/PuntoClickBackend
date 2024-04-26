package com.puntoclick.features.roles.model

import com.puntoclick.data.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class UpdateRoleRequest(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val name: String
)