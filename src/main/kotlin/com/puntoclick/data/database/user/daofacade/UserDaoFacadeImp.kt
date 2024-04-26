package com.puntoclick.data.database.user.daofacade

import com.puntoclick.data.database.dbQuery
import com.puntoclick.data.database.entity.User
import com.puntoclick.data.database.role.table.RoleTable
import com.puntoclick.data.database.team.table.TeamTable
import com.puntoclick.data.database.user.table.UserTable
import com.puntoclick.features.roles.model.RoleResponse
import com.puntoclick.features.team.model.TeamResponse
import com.puntoclick.features.user.model.UserResponse
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.*

class UserDaoFacadeImp : UserDaoFacade {

    override suspend fun allUsers(teamId: UUID): List<UserResponse> = dbQuery {
        UserTable.innerJoin(TeamTable).innerJoin(RoleTable).select {
            UserTable.team eq teamId //and (UserTable.type eq 2)
        }.map(::resultRowToUSer)
    }

    override suspend fun addUser(user: User): Boolean = dbQuery {
        UserTable.insert {
            it[name] = user.name
            it[lastName] = user.lastName
            it[email] = user.email
            it[phoneNumber] = user.phoneNumber
            it[password] = user.password
            it[type] = user.type
            it[role] = user.role
            it[team] = user.team
            it[validated] = user.validated
            it[birthday] = user.birthday
        }.resultedValues?.singleOrNull() != null
    }

    override suspend fun user(userId: UUID): UserResponse? = dbQuery {
        (UserTable innerJoin TeamTable innerJoin RoleTable)
            .select { UserTable.uuid eq userId }
            .mapNotNull(::resultRowToUSer)
            .singleOrNull()

    }

    override suspend fun updateUser(user: User): Boolean = dbQuery {
        val rowUpdated = UserTable.update({ UserTable.uuid eq user.id }) {
            it[name] = user.name
        }
        rowUpdated > 0
    }

    override suspend fun deleteUser(uuid: UUID): Boolean = dbQuery {
        val rowDeleted = UserTable.deleteWhere { UserTable.uuid eq uuid }
        rowDeleted > 0
    }


    private fun resultRowToUSer(row: ResultRow) = UserResponse(
        id = row[UserTable.uuid],
        name = row[UserTable.name],
        lastName = row[UserTable.lastName],
        email = row[UserTable.email],
        phoneNumber = row[UserTable.phoneNumber],
        birthday = row[UserTable.birthday],
        role = RoleResponse(
            id = row[RoleTable.uuid],
            name = row[RoleTable.name]
        ),
        team = TeamResponse(
            id = row[TeamTable.uuid],
            name = row[TeamTable.name],
        )
    )

}