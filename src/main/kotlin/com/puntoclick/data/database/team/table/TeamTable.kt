package com.puntoclick.data.database.team.table

import com.puntoclick.data.utils.NAME_LENGTH
import org.jetbrains.exposed.sql.Table


object TeamTable : Table("team") {
    val uuid = uuid("id").autoGenerate()
    val name = varchar("name", NAME_LENGTH)
    @Suppress("Unused")
    val createAt = long("createAt").default(System.currentTimeMillis())
    @Suppress("Unused")
    val updateAt = long("updateAt").default(System.currentTimeMillis())

    override val primaryKey: PrimaryKey = PrimaryKey(uuid)
}