package com.puntoclick.features.roles.controller

import com.puntoclick.data.database.entity.Role
import com.puntoclick.data.database.roledaofacade.RoleDaoFacade
import com.puntoclick.data.model.AppResponse
import com.puntoclick.data.model.ErrorResponse
import io.ktor.http.*
import java.lang.Exception

class RoleController(
    private val facade: RoleDaoFacade
) {

    suspend fun getRoles(): AppResponse<List<Role>> {
        return try {
            AppResponse(
                data = facade.allRoles()
            )
        } catch (e: Exception) {
            AppResponse(
                status = HttpStatusCode.InternalServerError.value,
                error = ErrorResponse(
                    title =  e.message ?: "",
                    description = e.message ?: ""
                )
            )
        }
    }

    suspend fun getRole() {

    }

    suspend fun addRole(name: String): Boolean {
        return facade.addRole(name)
    }

    suspend fun updateRole() {

    }

    suspend fun deleteRole() {

    }


}