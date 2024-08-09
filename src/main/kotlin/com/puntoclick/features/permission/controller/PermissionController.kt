package com.puntoclick.features.permission.controller

import com.puntoclick.data.database.permission.daofacade.PermissionDaoFacade
import com.puntoclick.data.database.user.daofacade.UserDaoFacade
import com.puntoclick.data.model.AppResult
import com.puntoclick.data.model.permission.*
import com.puntoclick.features.utils.StringResourcesKey
import com.puntoclick.features.utils.createError
import com.puntoclick.features.utils.getString
import io.ktor.http.*
import java.util.*

class PermissionController(
    private val userDaoFacade: UserDaoFacade,
    private val permissionDaoFacade: PermissionDaoFacade
) {

    suspend fun addPermission(
        userId: UUID,
        teamId: UUID,
        addPermissionRequest: AddPermissionRequest
    ): AppResult<String> {
        val userType = userDaoFacade.getUserType(userId) ?: return createError()
        val result = permissionDaoFacade.addPermission(
            userType = userType,
            roleType = addPermissionRequest.roleType,
            actionType = addPermissionRequest.actionType,
            moduleType = addPermissionRequest.moduleType,
            teamId = teamId
        )
        return when (result) {
            AddPermissionResult.AlreadyExists -> createError(descriptionKey = StringResourcesKey.PERMISSION_ALREADY_EXISTS_ERROR_KEY)
            AddPermissionResult.InsertFailed -> createError(descriptionKey = StringResourcesKey.PERMISSION_INSERT_FAILED_ERROR_KEY)
            AddPermissionResult.UserNotAdmin -> createError(descriptionKey = StringResourcesKey.PERMISSION_USER_NOT_ADMIN_ERROR_KEY)
            AddPermissionResult.Success -> AppResult.Success(
                data = StringResourcesKey.PERMISSION_SUCCESS_MESSAGE_KEY.getString(),
                appStatus = HttpStatusCode.OK
            )
        }
    }

    suspend fun updatePermission(
        userId: UUID,
        teamId: UUID,
        updatePermissionRequest: UpdatePermissionRequest
    ): AppResult<String> {
        val userType = userDaoFacade.getUserType(userId) ?: return createError()
        val (permissionId, roleType, actionName, moduleType) = updatePermissionRequest


        val result =
            permissionDaoFacade.updatePermission(userType, permissionId, roleType, actionName, moduleType, teamId)

        return when (result) {
            UpdatePermissionResult.AlreadyExists -> createError(descriptionKey = StringResourcesKey.PERMISSION_UPDATE_ALREADY_EXISTS_ERROR_KEY)
            UpdatePermissionResult.UpdateFailed -> createError(descriptionKey = StringResourcesKey.PERMISSION_UPDATE_FAILED_ERROR_KEY)
            UpdatePermissionResult.UserNotAdmin -> createError(descriptionKey = StringResourcesKey.PERMISSION_UPDATE_USER_NOT_ADMIN_ERROR_KEY)
            UpdatePermissionResult.Success -> AppResult.Success(
                data = StringResourcesKey.PERMISSION_UPDATE_SUCCESS_MESSAGE_KEY.getString(),
                appStatus = HttpStatusCode.OK
            )
        }
    }

    suspend fun deletePermission(
        userId: UUID, deletePermissionRequest: DeletePermissionRequest
    ): AppResult<String> {
        val userType = userDaoFacade.getUserType(userId) ?: return createError()
        val (permissionId) = deletePermissionRequest

        val result = permissionDaoFacade.deletePermission(permissionId, userType)

        return when (result) {
            DeletePermissionResult.DeleteFailed -> createError(descriptionKey = StringResourcesKey.PERMISSION_DELETE_FAILED_ERROR_KEY)
            DeletePermissionResult.PermissionNotFound -> createError(descriptionKey = StringResourcesKey.PERMISSION_DELETE_NOT_FOUND_ERROR_KEY)
            DeletePermissionResult.UserNotAdmin -> createError(descriptionKey = StringResourcesKey.PERMISSION_DELETE_USER_NOT_ADMIN_ERROR_KEY)
            DeletePermissionResult.Success -> AppResult.Success(
                data = StringResourcesKey.PERMISSION_DELETE_SUCCESS_MESSAGE_KEY.getString(),
                appStatus = HttpStatusCode.OK
            )
        }
    }

    suspend fun getPermissionByTeam(teamId: UUID): AppResult<List<PermissionInfo>> {
        val permission = permissionDaoFacade.getPermissionsByTeam(teamId)
        return AppResult.Success(data = permission, appStatus = HttpStatusCode.OK)
    }

    suspend fun getPermissionsByRoleAndTeam(roleId: UUID, teamId: UUID): AppResult<List<PermissionInfo>> {
        val permission = permissionDaoFacade.getPermissionsByRole(roleId, teamId)
        return AppResult.Success(data = permission, appStatus = HttpStatusCode.OK)
    }

}

