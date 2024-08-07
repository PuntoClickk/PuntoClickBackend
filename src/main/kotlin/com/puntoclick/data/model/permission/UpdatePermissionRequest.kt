package com.puntoclick.data.model.permission

import com.puntoclick.data.model.action.ActionType
import com.puntoclick.data.model.module.ModuleType
import com.puntoclick.data.model.role.RoleType
import com.puntoclick.data.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class UpdatePermissionRequest(
    @Serializable(with = UUIDSerializer::class)
    val permissionId: UUID,
    val roleType: RoleType,
    val actionType: ActionType,
    val moduleType: ModuleType
)
