package com.puntoclick.data.database.userstoreassignment.table

import com.puntoclick.data.database.store.table.StoreTable
import com.puntoclick.data.database.team.table.TeamTable
import com.puntoclick.data.database.user.table.UserTable
import org.jetbrains.exposed.sql.Table

object UserStoreAssignmentTable : Table("user_store_assignment") {
    val uuid = uuid("id").autoGenerate().uniqueIndex()
    val workerId = reference("worker_id", UserTable.uuid)
    val storeId = reference("store_id", StoreTable.uuid)
    val teamId = reference("team_id", TeamTable.uuid)
    @Suppress("Unused")
    val createdAt = long("created_at").default(System.currentTimeMillis())
    @Suppress("Unused")
    val updatedAt = long("updated_at").default(System.currentTimeMillis())

    override val primaryKey: PrimaryKey = PrimaryKey(uuid)
}
