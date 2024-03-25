package com.puntoclick.features.roles.controller

import com.puntoclick.data.database.entity.Role
import com.puntoclick.data.database.roledaofacade.RoleDaoFacade
import com.puntoclick.data.model.AppResult
import com.puntoclick.data.model.createError
import io.ktor.http.*
import java.lang.Exception

class RoleController(
    private val facade: RoleDaoFacade
) {

    suspend fun getRoles(): AppResult<List<Role>> {

        return try {
            AppResult.Success(
                data = facade.allRoles(), appStatus = HttpStatusCode.OK
            )
        } catch (e: Exception) {
            createError(e.message, e.message)
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
