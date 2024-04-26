package com.puntoclick.data.database.role.daofacade

import com.puntoclick.data.database.entity.Role
import com.puntoclick.features.roles.model.CreateRoleRequest
import com.puntoclick.features.roles.model.UpdateRoleRequest
import java.util.UUID

interface RoleDaoFacade {
    suspend fun allRoles(): List<Role>
    suspend fun role(uuid: UUID): Role?
    suspend fun addRole(createRoleRequest: CreateRoleRequest): Boolean
    suspend fun updateRole(updateRoleRequest: UpdateRoleRequest): Boolean
    suspend fun deleteRole(uuid: UUID): Boolean
}