package com.puntoclick.features.roles.controller

import com.puntoclick.data.database.role.daofacade.RoleDaoFacade
import com.puntoclick.data.model.AppResult
import com.puntoclick.data.utils.*
import com.puntoclick.data.model.role.CreateRoleRequest
import com.puntoclick.data.model.role.RoleResponse
import com.puntoclick.data.model.role.UpdateRoleRequest
import com.puntoclick.features.utils.*
import io.ktor.http.*
import java.util.*

class RoleController(
    private val facade: RoleDaoFacade
) {

    suspend fun getRoles(): AppResult<List<RoleResponse>> {
        return AppResult.Success(
            data = facade.allRoles(), appStatus = HttpStatusCode.OK
        )
    }

    suspend fun getRole(id: UUID): AppResult<RoleResponse> {
        val role = searchRole(id = id)
        return role?.let {
            AppResult.Success(
                data = it, appStatus = HttpStatusCode.OK
            )
        } ?: createError(title = ERROR_TITLE, GET_REGISTER, HttpStatusCode.NotFound)
    }

    private suspend fun searchRole(id: UUID): RoleResponse? {
        return facade.role(id)
    }

    suspend fun addRole(createRoleRequest: CreateRoleRequest): AppResult<Boolean> {
        return createRole(createRoleRequest)
    }

    suspend fun updateRole(updateRoleRequest: UpdateRoleRequest): AppResult<Boolean> {
        return updateRoleName(updateRoleRequest)
    }

    suspend fun deleteRole(id: UUID): AppResult<Boolean> {
        val result = facade.deleteRole(id)
        return if (result) {
            AppResult.Success(
                data = true,
                appStatus = HttpStatusCode.OK
            )
        } else {
            createError(
                title = ERROR_TITLE,
                description = DELETE_ERROR_MESSAGE,
                status = HttpStatusCode.NotFound
            )
        }
    }

    private suspend fun createRole(createRoleRequest: CreateRoleRequest): AppResult<Boolean> =
        if (facade.addRole(createRoleRequest)) {
            AppResult.Success(data = true, appStatus = HttpStatusCode.OK)
        } else createError(title = ERROR_TITLE, CREATE_BODY, HttpStatusCode.BadRequest)


    private suspend fun updateRoleName(updateRoleRequest: UpdateRoleRequest): AppResult<Boolean> =
        if (facade.updateRole(updateRoleRequest)) {
            AppResult.Success(data = true, appStatus = HttpStatusCode.OK)
        } else createError(title = ERROR_TITLE, UPDATE_BODY, HttpStatusCode.BadRequest)

}
