@file:Suppress("unused")

package com.puntoclick.data.database.user.table

import com.puntoclick.data.database.role.table.RoleTable
import com.puntoclick.data.database.team.table.TeamTable
import com.puntoclick.data.utils.*
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table


object UserTable : Table("user") {
    val uuid = uuid("id").autoGenerate()
    val name = varchar("name", NAME_LENGTH)
    val lastName = varchar("lastName", NAME_LENGTH)
    val email = varchar("email", EMAIL_LENGTH).uniqueIndex()
    val phoneNumber = varchar("phoneNumber", PHONE_NUMBER_LENGTH)
    val password = varchar("password",PASSWORD_LENGTH)
    val type = integer("type")
    val validated = bool("validated").default(false)
    val birthday = long("birthday")
    val role = reference("role", RoleTable.uuid, onDelete = ReferenceOption.NO_ACTION)
    val team = reference("team", TeamTable.uuid)
    val createAt = long("createAt").default(System.currentTimeMillis())
    val updateAt = long("updateAt").default(System.currentTimeMillis())

    override val primaryKey: PrimaryKey = PrimaryKey(uuid)

}
