package com.puntoclick.data.database.permission.table

import com.puntoclick.data.database.action.table.ActionTable
import com.puntoclick.data.database.module.table.ModuleTable
import com.puntoclick.data.database.role.table.RoleTable
import com.puntoclick.data.database.team.table.TeamTable
import org.jetbrains.exposed.sql.Table

object PermissionTable: Table("permission") {
    val uuid = uuid("id").autoGenerate().uniqueIndex()
    val action = reference("action", ActionTable.uuid)
    val role = reference("role", RoleTable.uuid)
    val module = reference("module", ModuleTable.uuid)
    val team = reference("team", TeamTable.uuid)
    @Suppress("unused")
    val createAt = long("createAt").default(System.currentTimeMillis())
    val updateAt = long("updateAt").default(System.currentTimeMillis())

    override val primaryKey: PrimaryKey = PrimaryKey(uuid)
}