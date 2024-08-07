package com.puntoclick.data.database.permission.daofacade

import com.puntoclick.data.model.action.ActionType
import com.puntoclick.data.model.module.ModuleType
import com.puntoclick.data.model.permission.AddPermissionResult
import com.puntoclick.data.model.permission.DeletePermissionResult
import com.puntoclick.data.model.permission.PermissionInfo
import com.puntoclick.data.model.permission.UpdatePermissionResult
import com.puntoclick.data.model.role.RoleType
import com.puntoclick.data.model.user.UserType
import java.util.*

interface PermissionDaoFacade {
    suspend fun initializePermissionsForTeam(teamId: UUID)
    suspend fun addPermission(userType: UserType, roleType: RoleType, actionType: ActionType, moduleType: ModuleType, teamId: UUID): AddPermissionResult
    suspend fun updatePermission(userType: UserType,permissionId: UUID, roleType: RoleType, actionType: ActionType, moduleType: ModuleType, teamId: UUID): UpdatePermissionResult
    suspend fun getPermissionsByTeam(teamId: UUID): List<PermissionInfo>
    suspend fun deletePermission(permissionId: UUID, userType: UserType): DeletePermissionResult
    suspend fun getPermissionsByRole(roleId: UUID,teamId: UUID): List<PermissionInfo>
    suspend fun hasPermission(roleId: UUID, actionType: ActionType, moduleType: ModuleType, teamId: UUID): Boolean
}

