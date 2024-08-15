package com.puntoclick.data.database.store.table

import com.puntoclick.data.database.team.table.TeamTable
import com.puntoclick.data.database.user.table.UserTable
import com.puntoclick.data.utils.LOCATION_LENGTH
import com.puntoclick.data.utils.NAME_LENGTH
import org.jetbrains.exposed.sql.Table

object StoreTable: Table("store") {
    val uuid = uuid("id").autoGenerate().uniqueIndex()
    val name = varchar("name", NAME_LENGTH)
    val location = varchar("location", LOCATION_LENGTH )
    val team = reference("team", TeamTable.uuid)
    val user = reference("user", UserTable.uuid)
    val isActive = bool("isActive").default(true)
    @Suppress("Unused")
    val createdAt = long("created_at").default(System.currentTimeMillis())
    @Suppress("Unused")
    val updatedAt = long("updated_at").default(System.currentTimeMillis())

    override val primaryKey: PrimaryKey = PrimaryKey(UserTable.uuid)
}