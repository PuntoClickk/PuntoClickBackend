package com.puntoclick.data.model.permission

import com.puntoclick.data.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class UpdatePermissionRequest(
    @Serializable(with = UUIDSerializer::class)
    val permissionId: UUID,
    @Serializable(with = UUIDSerializer::class)
    val roleId: UUID,
    @Serializable(with = UUIDSerializer::class)
    val actionId: UUID,
    @Serializable(with = UUIDSerializer::class)
    val moduleId: UUID
)
