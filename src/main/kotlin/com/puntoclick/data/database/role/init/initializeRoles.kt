package com.puntoclick.data.database.role.init

import com.puntoclick.data.database.role.table.RoleTable
import com.puntoclick.data.model.role.RoleType
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

fun initializeRoles() {
    RoleType.entries.forEach { role ->
        if (RoleTable.select { RoleTable.name eq role.roleName }.singleOrNull() == null) {
            RoleTable.insert {
                it[type] = role.type
                it[name] = role.roleName
                it[isActive] = true
            }
        }
    }
}