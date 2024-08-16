package com.puntoclick.data.database.init

import com.puntoclick.data.database.role.table.RoleTable
import com.puntoclick.data.model.GlobalRoleIds
import com.puntoclick.data.model.role.RoleType

fun initializeRolesIds() {
    RoleType.entries.forEach { roleType ->
        val roleId = RoleTable
            .select(RoleTable.uuid)
            .where { RoleTable.name eq roleType.roleName }
            .map { it[RoleTable.uuid] }
            .singleOrNull() ?: throw Exception("Role ${roleType.roleName} not found")
        GlobalRoleIds.set(roleType, roleId)
    }

}