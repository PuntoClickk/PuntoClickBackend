package com.puntoclick.features.roles.database

import com.puntoclick.features.roles.entity.Role
import java.util.UUID

interface RoleDaoFacade {
    suspend fun allRoles(): List<Role>
    suspend fun role(uuid: UUID): Role?
    suspend fun addRole(name: String): Boolean
    suspend fun updateRole(role: Role): Boolean
    suspend fun deleteRole(uuid: UUID): Boolean
}