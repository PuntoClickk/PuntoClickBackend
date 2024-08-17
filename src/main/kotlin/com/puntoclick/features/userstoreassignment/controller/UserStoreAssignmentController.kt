package com.puntoclick.features.userstoreassignment.controller

import com.puntoclick.data.database.permission.daofacade.PermissionDaoFacade
import com.puntoclick.data.database.userstoreassignment.daofacade.UserStoreAssignmentDaoFacade
import com.puntoclick.data.model.AppResult
import com.puntoclick.data.model.action.ActionType
import com.puntoclick.data.model.module.ModuleType
import com.puntoclick.data.model.store.StoreWithUserName
import com.puntoclick.data.model.user.BaseInfoUser
import com.puntoclick.data.model.userstoreassigment.UpdateUserStoreAssignmentRequest
import com.puntoclick.data.model.userstoreassigment.UserStoreAssignmentRequest
import com.puntoclick.data.model.userstoreassigment.UserStoreAssignmentResult
import com.puntoclick.data.model.userstoreassigment.UserStoreAssignmentWithDetails
import com.puntoclick.features.utils.StringResourcesKey
import com.puntoclick.features.utils.createError
import com.puntoclick.features.utils.createPermissionError
import com.puntoclick.features.utils.getString
import java.util.*

class UserStoreAssignmentController(
    private val userStoreAssignmentDaoFacade: UserStoreAssignmentDaoFacade,
    private val permissionDaoFacade: PermissionDaoFacade
) {

    private val module = ModuleType.USERS

    suspend fun assignUserToStore(
        assignRequest: UserStoreAssignmentRequest,
        locale: Locale,
        roleId: UUID,
        teamId: UUID
    ): AppResult<String> {
        val userAllowed = permissionDaoFacade.hasPermission(
            roleId = roleId,
            actionType = ActionType.WRITE,
            moduleType = module,
            teamId = teamId
        )

        if (!userAllowed) return locale.createPermissionError()

        val result = userStoreAssignmentDaoFacade.assignUserToStore(assignRequest,teamId)

        return result.handleFacadeResult(locale)
    }

    suspend fun getUserStoreAssignmentsByWorker(
        workerId: UUID,
        locale: Locale,
        roleId: UUID,
        teamId: UUID,
    ): AppResult<List<UserStoreAssignmentWithDetails>> {
        val userAllowed = permissionDaoFacade.hasPermission(
            roleId = roleId,
            actionType = ActionType.READ,
            moduleType = module,
            teamId = teamId
        )

        if (!userAllowed) {
            return locale.createPermissionError()
        }

        val assignments = userStoreAssignmentDaoFacade.getUserStoreAssignmentsByWorker(workerId, teamId)
        return AppResult.Success(data = assignments)
    }

    suspend fun removeUserFromStore(
        assignmentId: UUID,
        locale: Locale,
        roleId: UUID,
        teamId: UUID
    ): AppResult<String> {
        val userAllowed = permissionDaoFacade.hasPermission(
            roleId = roleId,
            actionType = ActionType.DELETE,
            moduleType = module,
            teamId = teamId
        )

        if (!userAllowed) return locale.createPermissionError()

        val result = userStoreAssignmentDaoFacade.removeUserFromStore(assignmentId)

        return result.handleFacadeResult(locale)
    }

    suspend fun getUsersByStore(
        storeId: UUID,
        locale: Locale,
        roleId: UUID,
        teamId: UUID
    ): AppResult<List<BaseInfoUser>> {
        val userAllowed = permissionDaoFacade.hasPermission(
            roleId = roleId,
            actionType = ActionType.READ,
            moduleType = module,
            teamId = teamId
        )

        if (!userAllowed) {
            return locale.createPermissionError()
        }

        val users = userStoreAssignmentDaoFacade.getUsersByStore(storeId)
        return AppResult.Success(data = users)
    }

    suspend fun getStoresByWorker(
        workerId: UUID,
        locale: Locale,
        roleId: UUID,
        teamId: UUID
    ): AppResult<List<StoreWithUserName>> {
        val userAllowed = permissionDaoFacade.hasPermission(
            roleId = roleId,
            actionType = ActionType.READ,
            moduleType = module,
            teamId = teamId
        )

        if (!userAllowed) {
            return locale.createPermissionError()
        }

        val stores = userStoreAssignmentDaoFacade.getStoresByWorker(workerId, teamId)
        return AppResult.Success(data = stores)
    }

    suspend fun updateUserStoreAssignment(
        request: UpdateUserStoreAssignmentRequest,
        locale: Locale,
        roleId: UUID,
        teamId: UUID
    ): AppResult<String> {
        val userAllowed = permissionDaoFacade.hasPermission(
            roleId = roleId,
            actionType = ActionType.UPDATE,
            moduleType = module,
            teamId = teamId
        )

        if (!userAllowed) return locale.createPermissionError()

        val result = userStoreAssignmentDaoFacade.updateUserStoreAssignment(request)

        return result.handleFacadeResult(locale)
    }


    private fun UserStoreAssignmentResult.handleFacadeResult(locale: Locale): AppResult<String> = when (this) {
        UserStoreAssignmentResult.Success -> AppResult.Success(locale.getString(StringResourcesKey.USER_STORE_ASSIGNMENT_OPERATION_SUCCESS_MESSAGE_KEY))
        UserStoreAssignmentResult.AlreadyAssigned -> locale.createError(descriptionKey = StringResourcesKey.USER_ALREADY_ASSIGNED_ERROR_KEY)
        UserStoreAssignmentResult.AssignmentFailed -> locale.createError(descriptionKey = StringResourcesKey.USER_STORE_ASSIGNMENT_FAILED_ERROR_KEY)
        UserStoreAssignmentResult.NotFound -> locale.createError(descriptionKey = StringResourcesKey.USER_STORE_ASSIGNMENT_NOT_FOUND_ERROR_KEY)
    }
}