package com.puntoclick.features.roles.controller

import com.puntoclick.data.database.entity.Role
import com.puntoclick.data.database.role.daofacade.RoleDaoFacade
import com.puntoclick.data.model.AppResult
import com.puntoclick.features.roles.model.CreateRoleRequest
import com.puntoclick.features.roles.model.UpdateRoleRequest
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

    suspend fun addRole(createRoleRequest: CreateRoleRequest): AppResult<Boolean> = tryCatch {
        val validatedName = createRoleRequest.name.validateRequestString()
        validatedName?.let { createRole(createRoleRequest) } ?:
        createError(title = "NError", "Desc Error", HttpStatusCode.BadRequest)
    }

    suspend fun updateRole(updateRoleRequest: UpdateRoleRequest): AppResult<Boolean> = tryCatch {
        val validatedName = updateRoleRequest.name.validateRequestString()
        validatedName?.let {
            updateRoleName(updateRoleRequest)
        } ?: createError(title = "Not valid", "Desc Error", HttpStatusCode.BadRequest)
    }

    suspend fun deleteRole(id: UUID): AppResult<Boolean> = tryCatch {
        val result = facade.deleteRole(id)
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

    private suspend fun createRole(createRoleRequest: CreateRoleRequest): AppResult<Boolean> =
        if (facade.addRole(createRoleRequest)) {
            AppResult.Success(data = true, appStatus = HttpStatusCode.OK)
        } else createError(title = "NError", "Desc Error", HttpStatusCode.BadRequest)


    private suspend fun updateRoleName(updateRoleRequest: UpdateRoleRequest): AppResult<Boolean> =
        if (facade.updateRole(updateRoleRequest)) {
            AppResult.Success(
                data = true, appStatus = HttpStatusCode.OK
            )
        } else createError(title = "Not found", "Desc Error", HttpStatusCode.BadRequest)

}
