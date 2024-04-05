package com.puntoclick.data.database.role.table

import org.jetbrains.exposed.sql.Table


object RoleTable : Table("role") {
    val uuid = uuid("id").autoGenerate()
    val name = varchar("name", 30)
    val createAt = long("createAt").default(System.currentTimeMillis())
    val updateAt = long("updateAt").default(System.currentTimeMillis())

    override val primaryKey: PrimaryKey = PrimaryKey(uuid)
}