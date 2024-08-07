package com.puntoclick.data.model

import com.puntoclick.data.model.role.RoleType
import java.util.*

object GlobalRoleIds {
    private val roleIdMap: MutableMap<RoleType, UUID> = mutableMapOf()

    fun get(roleType: RoleType): UUID {
        return roleIdMap[roleType] ?: throw Exception("Role ID not found for type: $roleType")
    }

    fun set(roleType: RoleType, id: UUID) {
        roleIdMap[roleType] = id
    }
}