package com.puntoclick.features.store.controller

import com.puntoclick.data.database.permission.daofacade.PermissionDaoFacade
import com.puntoclick.data.database.store.facade.StoreDaoFacade
import com.puntoclick.data.model.AppResult
import com.puntoclick.data.model.UUIDAppRequest
import com.puntoclick.data.model.action.ActionType
import com.puntoclick.data.model.module.ModuleType
import com.puntoclick.data.model.store.*
import com.puntoclick.features.utils.StringResourcesKey
import com.puntoclick.features.utils.createError
import com.puntoclick.features.utils.getString
import java.util.Locale
import java.util.UUID

class StoreController(
    private val storeDaoFacade: StoreDaoFacade,
    private val permissionDaoFacade: PermissionDaoFacade
) {

    private val module = ModuleType.STORES

    suspend fun createStore(
        createStoreRequest: CreateStoreRequest,
        locale: Locale,
        userId: UUID,
        roleId: UUID,
        teamId: UUID
    ): AppResult<String> {
        val userAllowed = permissionDaoFacade.hasPermission(
            roleId = roleId,
            actionType = ActionType.WRITE,
            moduleType = module,
            teamId = teamId
        )

        if (!userAllowed) return locale.createError()

        val result = if (storeDaoFacade.storeExists(createStoreRequest.name, teamId)) CreateStoreResult.AlreadyExists
        else storeDaoFacade.createStore(createStoreRequest.toStoreData(teamId, userId))

        return when (result) {
            CreateStoreResult.AlreadyExists -> locale.createError(descriptionKey = StringResourcesKey.STORE_NAME_ALREADY_EXISTS_ERROR_KEY)
            CreateStoreResult.InsertFailed -> locale.createError()
            CreateStoreResult.Success -> AppResult.Success(data = locale.getString(StringResourcesKey.STORE_CREATE_SUCCESS_MESSAGE_KEY))
        }
    }

    suspend fun getStoresWithUserNamesByTeam(
        teamId: UUID,
        locale: Locale,
        roleId: UUID
    ): AppResult<List<StoreWithUserName>> {
        val userAllowed = permissionDaoFacade.hasPermission(
            roleId = roleId,
            actionType = ActionType.READ,
            moduleType = module,
            teamId = teamId
        )

        if (!userAllowed) {
            return locale.createError()
        }

        val stores = storeDaoFacade.getStoresWithUserNamesByTeam(teamId)
        return AppResult.Success(data = stores)
    }

    suspend fun updateStore(
        updateStoreRequest: UpdateStoreRequest,
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

        if (!userAllowed) return locale.createError()

        val result = if (storeDaoFacade.storeExists(updateStoreRequest.name.trim(), teamId)) UpdateStoreResult.AlreadyExists
        else storeDaoFacade.updateStore(updateStoreRequest.id, updateStoreRequest.name, updateStoreRequest.location)

        return when (result) {
            UpdateStoreResult.NotFound -> locale.createError(descriptionKey = StringResourcesKey.STORE_NOT_FOUND_ERROR_KEY)
            UpdateStoreResult.UpdateFailed -> locale.createError(descriptionKey = StringResourcesKey.STORE_UPDATE_FAILED_ERROR_KEY)
            UpdateStoreResult.Success -> AppResult.Success(data = locale.getString(StringResourcesKey.STORE_UPDATE_SUCCESS_MESSAGE_KEY))
            UpdateStoreResult.AlreadyExists -> locale.createError(descriptionKey = StringResourcesKey.STORE_NAME_ALREADY_EXISTS_ERROR_KEY)
        }
    }

    suspend fun deleteStore(
        request: UUIDAppRequest,
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

        if (!userAllowed) {
            return locale.createError(descriptionKey = StringResourcesKey.PERMISSION_DENIED_ERROR_KEY)
        }

        val result = storeDaoFacade.deleteStore(request.id)

        return when (result) {
            DeleteStoreResult.NotFound -> locale.createError(descriptionKey = StringResourcesKey.STORE_NOT_FOUND_ERROR_KEY)
            DeleteStoreResult.DeleteFailed -> locale.createError()
            DeleteStoreResult.Success -> AppResult.Success(data = locale.getString(StringResourcesKey.STORE_DELETE_SUCCESS_MESSAGE_KEY))
        }
    }

}