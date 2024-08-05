package com.puntoclick.data.database.user.daofacade

import com.puntoclick.data.database.entity.User
import com.puntoclick.data.model.auth.CreateUserData
import com.puntoclick.data.model.user.BaseInfoUser
import com.puntoclick.data.model.user.UserLogin
import com.puntoclick.data.model.user.UserResponse
import java.util.*

interface UserDaoFacade {
    suspend fun allUsers(teamId: UUID): List<UserResponse>
    suspend fun assignUserToTeam(userId: UUID, teamId: UUID): Boolean
    suspend fun user(userId: UUID): UserResponse?
    suspend fun getBaseInfoUser(email: String): BaseInfoUser?
    suspend fun user(email: String): UserLogin?
    suspend fun addUser(user: CreateUserData): Boolean
    suspend fun updateUser(user: User): Boolean
    suspend fun deleteUser(uuid: UUID): Boolean
    suspend fun emailExists(email: String): Boolean
}