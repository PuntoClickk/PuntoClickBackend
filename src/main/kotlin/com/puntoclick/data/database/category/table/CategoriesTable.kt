package com.puntoclick.data.database.category.table

import com.puntoclick.data.database.team.table.TeamTable
import com.puntoclick.data.database.user.table.UserTable
import org.jetbrains.exposed.sql.Table

object CategoriesTable: Table("Category") {
    val uuid = uuid("id").autoGenerate().uniqueIndex()
    val name = varchar("name", 11)
    val user = reference("user", UserTable.uuid)
    val team = reference("team", TeamTable.uuid)
    val createdAt = long("createdAt").default(System.currentTimeMillis())
    val updatedAt = long("updatedAt").default(System.currentTimeMillis())

    override val primaryKey: PrimaryKey = PrimaryKey(uuid)
}