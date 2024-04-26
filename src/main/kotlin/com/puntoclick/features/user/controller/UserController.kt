package com.puntoclick.features.user.controller

import com.puntoclick.data.database.entity.User
import com.puntoclick.data.database.user.daofacade.UserDaoFacade
import com.puntoclick.data.model.AppResult
import com.puntoclick.features.user.model.CreateUserRequest
import com.puntoclick.features.user.model.UserResponse
import com.puntoclick.features.utils.createError
import com.puntoclick.features.utils.tryCatch
import io.ktor.http.*
import java.util.*

class UserController(
    private val userDaoFacade: UserDaoFacade,
) {

    suspend fun allUsers(teamId: UUID): AppResult<List<UserResponse>> = tryCatch {
        val users = userDaoFacade.allUsers(teamId)
        AppResult.Success(data = users, appStatus = HttpStatusCode.OK)
    }

    suspend fun getUser(userId: UUID): AppResult<UserResponse> = tryCatch {
        val user = userDaoFacade.user(userId = userId)
        user?.let {
                AppResult.Success(data = it, appStatus = HttpStatusCode.OK) } ?:
                createError(
            "No user",
            "No User found",
            HttpStatusCode.NotFound
        )
    }

    suspend fun createUser(createUserRequest: CreateUserRequest): AppResult<Boolean> = tryCatch {
        if (userDaoFacade.addUser(createUserRequest)) AppResult.Success(data = true, appStatus = HttpStatusCode.OK)
        else createError(title = "NError", "Desc Error", HttpStatusCode.BadRequest)
    }

    suspend fun updateUser(user: User): AppResult<Boolean> = tryCatch {
        if (userDaoFacade.updateUser(user)) AppResult.Success(data = true, appStatus = HttpStatusCode.OK)
        else createError(title = "Error", description = "User not updated")
    }

    suspend fun deleteUser(userId: UUID): AppResult<Boolean> = tryCatch {
        if (userDaoFacade.deleteUser(userId)) AppResult.Success(data = true, appStatus = HttpStatusCode.OK)
        else createError(title = "Error", description = "User not deleted")
    }

}

