package com.puntoclick.data.database.user.daofacade

import com.puntoclick.data.database.entity.User
import java.util.*

interface UserDaoFacade {
    suspend fun allUsers(teamId: UUID): List<User>
    //suspend fun user(uuid: UUID): User?
    suspend fun addUser(user: User): Boolean
   /* suspend fun updateUser(user: User): Boolean
    suspend fun deleteUser(uuid: UUID): Boolean*/
}