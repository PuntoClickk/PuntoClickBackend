package com.puntoclick.data.database.action.table

import com.puntoclick.data.database.module.table.ModuleTable
import org.jetbrains.exposed.sql.Table


object ActionTable : Table("action") {
    val uuid = uuid("id").autoGenerate().uniqueIndex()
    val name = varchar("name", 7)
    @Suppress("unused")
    val createAt = long("createAt").default(System.currentTimeMillis())
    val updateAt = long("updateAt").default(System.currentTimeMillis())

    override val primaryKey: PrimaryKey = PrimaryKey(ModuleTable.uuid)
}
