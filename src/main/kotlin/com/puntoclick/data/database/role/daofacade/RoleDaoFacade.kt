package com.puntoclick.data.database.role.daofacade

import com.puntoclick.data.model.role.CreateRoleRequest
import com.puntoclick.data.model.role.RoleResponse
import com.puntoclick.data.model.role.UpdateRoleRequest
import java.util.UUID

interface RoleDaoFacade {
    suspend fun allRoles(): List<RoleResponse>
    suspend fun role(uuid: UUID): RoleResponse?
    suspend fun addRole(createRoleRequest: CreateRoleRequest): Boolean
    suspend fun updateRole(updateRoleRequest: UpdateRoleRequest): Boolean
    suspend fun deleteRole(uuid: UUID): Boolean
}