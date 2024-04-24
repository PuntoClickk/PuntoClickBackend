package com.puntoclick.data.database.user.table

import com.puntoclick.data.database.role.table.RoleTable
import com.puntoclick.data.database.team.table.TeamTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table


object UserTable : Table("user") {
    val uuid = uuid("id").autoGenerate()
    val name = varchar("name", 30)
    val lastName = varchar("lastName", 30)
    val email = varchar("email", 50)
    val phoneNumber = varchar("phoneNumber", 13)
    val password = varchar("password",200)
    val type = integer("type")
    val validated = bool("validated").default(false)
    val birthday = long("birthday")
    val role = reference("role", RoleTable.uuid, onDelete = ReferenceOption.NO_ACTION)
    val team = reference("team", TeamTable.uuid)
    val createAt = long("createAt").default(System.currentTimeMillis())
    val updateAt = long("updateAt").default(System.currentTimeMillis())

    override val primaryKey: PrimaryKey = PrimaryKey(uuid)

}
