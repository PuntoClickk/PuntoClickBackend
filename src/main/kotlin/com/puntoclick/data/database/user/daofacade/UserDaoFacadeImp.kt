package com.puntoclick.data.database.user.daofacade

import com.puntoclick.data.database.dbQuery
import com.puntoclick.data.database.entity.User
import com.puntoclick.data.database.registeruser.RegisterUserTable
import com.puntoclick.data.database.role.table.RoleTable
import com.puntoclick.data.database.team.table.TeamTable
import com.puntoclick.data.database.user.table.UserTable
import com.puntoclick.data.model.role.RoleResponse
import com.puntoclick.data.model.team.TeamResponse
import com.puntoclick.data.model.auth.CreateUserData
import com.puntoclick.data.model.user.*
import com.puntoclick.features.utils.escapeSingleQuotes
import com.puntoclick.security.hashPassword
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.*

class UserDaoFacadeImp : UserDaoFacade {

    override suspend fun allUsers(teamId: UUID): List<UserResponse> = dbQuery {
        (UserTable innerJoin RegisterUserTable innerJoin TeamTable innerJoin RoleTable).selectAll()
            .where { RegisterUserTable.team eq teamId and (UserTable.type eq 2) }.map(::resultRowToUser)
    }

    override suspend fun addUser(user: CreateUserData): Boolean = dbQuery {
        UserTable.insert {
            it[name] = user.name.escapeSingleQuotes()
            it[lastName] = user.lastName.escapeSingleQuotes()
            it[email] = user.email.escapeSingleQuotes()
            it[phoneNumber] = user.phoneNumber.escapeSingleQuotes()
            it[password] = user.password.escapeSingleQuotes().hashPassword()
            it[type] = user.type
            it[role] = user.role
            it[birthday] = user.birthday
            it[isActive] = user.isActive

        }.resultedValues?.singleOrNull()?.let { row ->
            val userId = row[UserTable.uuid]
            RegisterUserTable.insert {
                it[this.user] = userId
                it[this.team] = user.team
                it[isPending] = true
            }
        } != null
    }

    override suspend fun user(userId: UUID): UserResponse? = dbQuery {
        (UserTable innerJoin RegisterUserTable innerJoin TeamTable innerJoin RoleTable)
            .selectAll().where { UserTable.uuid eq userId }
            .mapNotNull(::resultRowToUser)
            .singleOrNull()

    }


    override suspend fun getUserType(userId: UUID): UserType? = dbQuery {
        UserTable
            .selectAll().where { UserTable.uuid eq userId }
            .mapNotNull(::resultRowToUserType)
            .singleOrNull()
    }

    override suspend fun assignUserToTeam(userId: UUID, teamId: UUID): Boolean = dbQuery {
        RegisterUserTable.insert {
            it[this.user] = userId
            it[this.team] = teamId
            it[isPending] = false
        }.resultedValues?.singleOrNull() != null
    }

    override suspend fun getBaseInfoUser(email: String): BaseInfoUser? = dbQuery {
        UserTable
            .selectAll().where { UserTable.email eq email }
            .mapNotNull(::resultRowToBaseInfoUser)
            .singleOrNull()
    }

    override suspend fun user(email: String): UserLogin? = dbQuery {
        (UserTable innerJoin RegisterUserTable).selectAll().where { UserTable.email eq email }.mapNotNull(::resultRowToUserLogin)
            .singleOrNull()
    }

    override suspend fun updateUser(user: User): Boolean = dbQuery {
        UserTable.update({ UserTable.uuid eq user.id }) {
            it[name] = user.name
        } > 0
    }

    override suspend fun deleteUser(uuid: UUID): Boolean = dbQuery {
        UserTable.deleteWhere { UserTable.uuid eq uuid } > 0
    }

    override suspend fun emailExists(email: String): Boolean = dbQuery {
        UserTable.selectAll().where { UserTable.email eq email }.count() > 0
    }

    private fun resultRowToUser(row: ResultRow) = UserResponse(
        id = row[UserTable.uuid],
        name = row[UserTable.name],
        lastName = row[UserTable.lastName],
        email = row[UserTable.email],
        phoneNumber = row[UserTable.phoneNumber],
        birthday = row[UserTable.birthday],
        role = RoleResponse(
            id = row[RoleTable.uuid],
            type = row[RoleTable.type],
            name = row[RoleTable.name],
            isActive = row[RoleTable.isActive]
        ),
        team = TeamResponse(
            id = row[TeamTable.uuid],
            name = row[TeamTable.name],
        )
    )

    private fun resultRowToUserLogin(row: ResultRow) = UserLogin(
        id = row[UserTable.uuid],
        password = row[UserTable.password],
        teamUUID = row[RegisterUserTable.team],
        roleUUID = row[UserTable.role],
        isActive = row[UserTable.isActive],
        isLocked = row[UserTable.isLocked]
    )

    private fun resultRowToBaseInfoUser(row: ResultRow) = BaseInfoUser(
        id = row[UserTable.uuid],
        name = row[UserTable.name],
        lastName = row[UserTable.lastName],
        phoneNumber = row[UserTable.phoneNumber],
        birthday = row[UserTable.birthday],
    )

    private fun resultRowToUserType(row: ResultRow): UserType? =
        UserType.entries.firstOrNull { row[UserTable.type] == it.type }


}