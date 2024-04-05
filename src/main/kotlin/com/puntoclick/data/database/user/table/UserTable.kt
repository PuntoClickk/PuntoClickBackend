package com.puntoclick.data.database.user.table

import com.puntoclick.data.database.role.table.RoleTable
import org.jetbrains.exposed.sql.Table


object UserTable : Table("role") {
    val uuid = uuid("id").autoGenerate()
    val name = varchar("name", 30)
    val createAt = long("createAt").default(System.currentTimeMillis())
    val updateAt = long("updateAt").default(System.currentTimeMillis())
    val role = reference("role", RoleTable.uuid)

    override val primaryKey: PrimaryKey = PrimaryKey(uuid)
}