package com.puntoclick.data.database.registeruser

import com.puntoclick.data.database.team.table.TeamTable
import com.puntoclick.data.database.user.table.UserTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object RegisterUserTable : Table("register_user") {
    val uuid = uuid("id").autoGenerate().uniqueIndex()
    val team = reference("team", TeamTable.uuid, onDelete = ReferenceOption.NO_ACTION)
    val user = reference("user", UserTable.uuid, onDelete = ReferenceOption.NO_ACTION)
    val isPending = bool("isPending").default(true)
    @Suppress("unused")
    val createAt = long("createAt").default(System.currentTimeMillis())
    val updateAt = long("updateAt").default(System.currentTimeMillis())

    override val primaryKey: PrimaryKey = PrimaryKey(uuid)
}