package com.puntoclick.data.database.user.daofacade

import com.puntoclick.data.database.entity.User
import com.puntoclick.features.user.model.CreateUserRequest
import com.puntoclick.features.user.model.UserResponse
import java.util.*

interface UserDaoFacade {
    suspend fun allUsers(teamId: UUID): List<UserResponse>
    suspend fun user(userId: UUID): UserResponse?
    suspend fun addUser(user: CreateUserRequest): Boolean
    suspend fun updateUser(user: User): Boolean
    suspend fun deleteUser(uuid: UUID): Boolean
}