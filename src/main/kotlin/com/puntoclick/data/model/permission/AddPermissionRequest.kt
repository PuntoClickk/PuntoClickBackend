package com.puntoclick.data.model.permission

import com.puntoclick.data.model.action.ActionType
import com.puntoclick.data.model.module.ModuleType
import com.puntoclick.data.model.role.RoleType
import kotlinx.serialization.Serializable

@Serializable
data class AddPermissionRequest(
    val roleType: RoleType,
    val actionType: ActionType,
    val moduleType: ModuleType
)
