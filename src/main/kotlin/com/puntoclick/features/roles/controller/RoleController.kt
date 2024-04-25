package com.puntoclick.features.roles.controller

import com.puntoclick.data.database.entity.Role
import com.puntoclick.data.database.role.daofacade.RoleDaoFacade
import com.puntoclick.data.model.AppResult
import com.puntoclick.features.utils.*
import io.ktor.http.*
import java.util.*

class RoleController(
    private val facade: RoleDaoFacade
) {

    suspend fun getRoles(): AppResult<List<Role>> = tryCatch {
        AppResult.Success(
            data = facade.allRoles(), appStatus = HttpStatusCode.OK
        )
    }

    suspend fun getRole(id: UUID) = tryCatch {
        val role = searchRole(id = id)
        role?.let {
            AppResult.Success(
                data = it, appStatus = HttpStatusCode.OK
            )
        } ?: createError(title = NOT_FOUND_OBJECT_TITLE, NOT_FOUND_OBJECT_DESCRIPTION, HttpStatusCode.NotFound)
    }

    private suspend fun searchRole(id: UUID): Role? {
        return facade.role(id)
    }

    suspend fun addRole(name: String): AppResult<Boolean> = tryCatch {
        val validatedName = name.validateRequestString()
        validatedName?.let { createRole(it) } ?:
        createError(title = "NError", "Desc Error", HttpStatusCode.BadRequest)
    }

    suspend fun updateRole(id: UUID, name: String): AppResult<Boolean> = tryCatch {
        val validatedName = name.validateRequestString()
        validatedName?.let { updateRoleName(id, name) } ?:
        createError(title = "NError", "Desc Error", HttpStatusCode.BadRequest)

    }

    suspend fun deleteRole(id: UUID): AppResult<Boolean> = tryCatch {
        val role = searchRole(id = id) ?: run {
            return@tryCatch createError(
                title = NOT_FOUND_OBJECT_TITLE,
                description = NOT_FOUND_OBJECT_DESCRIPTION,
                status = HttpStatusCode.NotFound
            )
        }

        val result = facade.deleteRole(role.id)
        if (result) {
            AppResult.Success(
                data = true,
                appStatus = HttpStatusCode.OK
            )
        } else {
            createError(
                title = NOT_FOUND_OBJECT_TITLE,
                description = NOT_FOUND_OBJECT_DESCRIPTION,
                status = HttpStatusCode.NotFound
            )
        }
    }

    private suspend fun createRole(name: String): AppResult<Boolean> =
        if (facade.addRole(name)) {
            AppResult.Success(
                data = true, appStatus = HttpStatusCode.OK
            )
        } else createError(title = "NError", "Desc Error", HttpStatusCode.BadRequest)


    private suspend fun updateRoleName(id: UUID, name: String): AppResult<Boolean> {
        val role = searchRole(id = id)
        return role?.let {
            facade.updateRole(it.copy(name = name))
            AppResult.Success(
                data = true, appStatus = HttpStatusCode.OK
            )
        } ?: createError(title = NOT_FOUND_OBJECT_TITLE, NOT_FOUND_OBJECT_DESCRIPTION, HttpStatusCode.NotFound)
    }
}
