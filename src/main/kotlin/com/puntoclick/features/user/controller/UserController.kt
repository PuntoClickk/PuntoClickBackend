package com.puntoclick.features.user.controller

import com.puntoclick.data.database.entity.User
import com.puntoclick.data.database.user.daofacade.UserDaoFacade
import com.puntoclick.data.model.AppResult
import com.puntoclick.data.model.user.UserResponse
import com.puntoclick.features.utils.StringResourcesKey
import com.puntoclick.features.utils.createError
import io.ktor.http.*
import java.util.*

class UserController(
    private val userDaoFacade: UserDaoFacade,
) {

    suspend fun allUsers(teamId: UUID): AppResult<List<UserResponse>> {
        val users = userDaoFacade.allUsers(teamId)
        return AppResult.Success(data = users, appStatus = HttpStatusCode.OK)
    }

    suspend fun getUser(userId: UUID, locale: Locale): AppResult<UserResponse> {
        val user = userDaoFacade.user(userId = userId)
        return user?.let {
            AppResult.Success(data = it, appStatus = HttpStatusCode.OK)
        } ?: locale.createError(descriptionKey = StringResourcesKey.NO_USER_FOUND_ERROR_KEY)
    }

    suspend fun updateUser(user: User, locale: Locale): AppResult<Boolean> {
        return if (userDaoFacade.updateUser(user)) AppResult.Success(data = true, appStatus = HttpStatusCode.OK)
        else locale.createError(descriptionKey = StringResourcesKey.USER_NOT_UPDATED_ERROR_KEY)
    }

    suspend fun deleteUser(userId: UUID, locale: Locale): AppResult<Boolean> {
        return if (userDaoFacade.deleteUser(userId)) AppResult.Success(data = true, appStatus = HttpStatusCode.OK)
        else locale.createError(descriptionKey = StringResourcesKey.USER_NOT_DELETED_ERROR_KEY)
    }

}

