package com.puntoclick.data.database.invitation.table

import com.puntoclick.data.database.team.table.TeamTable
import org.jetbrains.exposed.sql.Table


object InvitationTable : Table("invitation") {
    val uuid = uuid("id").autoGenerate().uniqueIndex()
    val team = reference("team", TeamTable.uuid)
    val code = varchar("code", 255).uniqueIndex()
    val createdAt = long("created_at").default(System.currentTimeMillis())
    val updatedAt = long("updated_at").default(System.currentTimeMillis())
    val expiresAt = long("expires_at")

    override val primaryKey: PrimaryKey = PrimaryKey(uuid)
}
