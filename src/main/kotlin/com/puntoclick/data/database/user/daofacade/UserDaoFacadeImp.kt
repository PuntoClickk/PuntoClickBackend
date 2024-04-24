package com.puntoclick.data.database.user.daofacade

import com.puntoclick.data.database.dbQuery
import com.puntoclick.data.database.entity.User
import com.puntoclick.data.database.role.table.RoleTable
import com.puntoclick.data.database.team.table.TeamTable
import com.puntoclick.data.database.user.table.UserTable
import org.jetbrains.exposed.sql.*
import java.util.*

class UserDaoFacadeImp: UserDaoFacade {
    override suspend fun allUsers(teamId: UUID): List<User> = dbQuery {

        val query = UserTable.innerJoin(TeamTable).innerJoin(RoleTable) .select {
            UserTable.team eq teamId and (UserTable.type eq 2)

        }

        query.forEach { row ->
            val userName = row[UserTable.name]
            val teamName = row[TeamTable.name]
            val roleName = row[RoleTable.name]

            println("User: $userName, Team: $teamName")
        }







        UserTable.select {
            (UserTable.team eq teamId) and ( UserTable.type eq 2)
        }.map(::resultRowToUser)

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

    /* override suspend fun user(uuid: UUID): User? {
         TODO("Not yet implemented")
     }

     override suspend fun addUser(user: User): Boolean {
         TODO("Not yet implemented")
     }

     override suspend fun updateUser(user: User): Boolean {
         TODO("Not yet implemented")
     }

     override suspend fun deleteUser(uuid: UUID): Boolean {
         TODO("Not yet implemented")
     }*/




    private fun resultRowToUser(row: ResultRow) = User(
        id = row[UserTable.uuid],
        name = row[UserTable.name],
        lastName = row[UserTable.lastName],
        email = row[UserTable.email],
        phoneNumber = row[UserTable.phoneNumber],
        type = row[UserTable.type],
        password = row[UserTable.password],
        role = row[UserTable.role],
        team = row[UserTable.team],
        validated = row[UserTable.validated],
        birthday = row[UserTable.birthday],
        createdAt = row[UserTable.createAt],
        updateAt = row[UserTable.updateAt],
    )

}