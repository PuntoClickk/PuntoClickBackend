package com.puntoclick.data.database.module.table

import org.jetbrains.exposed.sql.Table


object ModuleTable: Table("module") {
    val uuid = uuid("id").autoGenerate().uniqueIndex()
    val name = varchar("name", 11)
    @Suppress("unused")
    val createAt = long("createAt").default(System.currentTimeMillis())
    val updateAt = long("updateAt").default(System.currentTimeMillis())

    override val primaryKey: PrimaryKey = PrimaryKey(uuid)
}