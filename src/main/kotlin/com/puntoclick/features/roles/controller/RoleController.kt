package com.puntoclick.features.roles.controller

import com.puntoclick.data.database.entity.Role
import com.puntoclick.data.database.roledaofacade.RoleDaoFacade
import com.puntoclick.data.model.AppResult
import com.puntoclick.features.utils.tryCatch
import io.ktor.http.*

class RoleController(
    private val facade: RoleDaoFacade
) {

    suspend fun getRoles(): AppResult<List<Role>> = tryCatch {
        AppResult.Success(
            data = facade.allRoles(), appStatus = HttpStatusCode.OK
        )
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
