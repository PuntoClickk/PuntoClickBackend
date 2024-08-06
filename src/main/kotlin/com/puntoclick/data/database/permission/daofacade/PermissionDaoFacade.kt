package com.puntoclick.data.database.permission.daofacade

import com.puntoclick.data.model.permission.AddPermissionResult
import com.puntoclick.data.model.permission.DeletePermissionResult
import com.puntoclick.data.model.permission.PermissionInfo
import com.puntoclick.data.model.permission.UpdatePermissionResult
import com.puntoclick.data.model.user.UserType
import java.util.*

interface PermissionDaoFacade {
    suspend fun initializePermissionsForTeam(teamId: UUID)
    suspend fun addPermission(userType: UserType,roleId: UUID, actionId: UUID, moduleId: UUID, teamId: UUID): AddPermissionResult
    suspend fun updatePermission(userType: UserType,permissionId: UUID, roleId: UUID, actionId: UUID, moduleId: UUID, teamId: UUID): UpdatePermissionResult
    suspend fun getPermissionsByTeam(teamId: UUID): List<PermissionInfo>
    suspend fun deletePermission(permissionId: UUID, userType: UserType): DeletePermissionResult
    suspend fun hasPermission(roleId: UUID, actionId: UUID, moduleId: UUID, teamId: UUID): Boolean
}

