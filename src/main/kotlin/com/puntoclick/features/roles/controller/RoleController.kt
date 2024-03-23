package com.puntoclick.features.roles.controller

import com.puntoclick.data.database.entity.Role
import com.puntoclick.data.database.roledaofacade.RoleDaoFacade

class RoleController(
    private val facade: RoleDaoFacade
) {

    suspend fun getRoles(): List<Role> {
        return facade.allRoles()
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