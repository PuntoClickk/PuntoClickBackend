package com.puntoclick.data.database.user.daofacade

import com.puntoclick.data.database.entity.User
import com.puntoclick.data.model.user.CreateUserRequest
import com.puntoclick.data.model.user.UserResponse
import java.util.*

interface UserDaoFacade {
    suspend fun allUsers(teamId: UUID): List<UserResponse>
    suspend fun user(userId: UUID): UserResponse?
    suspend fun addUser(user: CreateUserRequest): Boolean
    suspend fun updateUser(user: User): Boolean
    suspend fun deleteUser(uuid: UUID): Boolean
}