package com.puntoclick.data.database.action.table

import org.jetbrains.exposed.sql.Table


object ActionTable : Table("action") {
    val uuid = uuid("id").autoGenerate().uniqueIndex()
    val name = varchar("name", 7)
    @Suppress("unused")
    val createAt = long("createAt").default(System.currentTimeMillis())
    @Suppress("unused")
    val updateAt = long("updateAt").default(System.currentTimeMillis())
}
