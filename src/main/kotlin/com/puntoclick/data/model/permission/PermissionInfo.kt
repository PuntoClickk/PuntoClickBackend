package com.puntoclick.data.model.permission

import com.puntoclick.data.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class PermissionInfo(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    @Serializable(with = UUIDSerializer::class)
    val roleId: UUID,
    val roleName: String,
    @Serializable(with = UUIDSerializer::class)
    val actionId: UUID,
    val actionName: String,
    @Serializable(with = UUIDSerializer::class)
    val moduleId: UUID,
    val moduleName: String,
    @Serializable(with = UUIDSerializer::class)
    val teamId: UUID,
)