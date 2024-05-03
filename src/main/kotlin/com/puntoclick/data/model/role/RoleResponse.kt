package com.puntoclick.data.model.role

import com.puntoclick.data.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class RoleResponse(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val type: Int,
    val name: String,
)
