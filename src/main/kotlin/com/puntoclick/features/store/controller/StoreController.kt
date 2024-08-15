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
import com.puntoclick.features.utils.createPermissionError
import com.puntoclick.features.utils.getString
import java.util.*

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

        if (!userAllowed) return locale.createPermissionError()

        val result = if (storeDaoFacade.storeExists(createStoreRequest.name, teamId)) StoreResult.AlreadyExists
        else storeDaoFacade.createStore(createStoreRequest.toStoreData(teamId, userId))

        return result.handleFacadeResult(locale)
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
            return locale.createPermissionError()
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

        if (!userAllowed) return locale.createPermissionError()

        val result = if (storeDaoFacade.storeExists(updateStoreRequest.name.trim(), teamId)) StoreResult.AlreadyExists
        else storeDaoFacade.updateStore(updateStoreRequest.id, updateStoreRequest.name, updateStoreRequest.location)


        return result.handleFacadeResult(locale)
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

        if (!userAllowed) return locale.createPermissionError()

        val result = storeDaoFacade.deleteStore(request.id)

        return result.handleFacadeResult(locale)
    }

    private fun StoreResult.handleFacadeResult(locale: Locale): AppResult<String> = when (this) {
        StoreResult.Success -> AppResult.Success(locale.getString(StringResourcesKey.STORE_OPERATION_SUCCESS_MESSAGE_KEY))
        StoreResult.AlreadyExists -> locale.createError(descriptionKey = StringResourcesKey.STORE_NAME_ALREADY_EXISTS_ERROR_KEY)
        StoreResult.DeleteFailed -> locale.createError(descriptionKey = StringResourcesKey.STORE_DELETE_FAILED_ERROR_KEY)
        StoreResult.InsertFailed -> locale.createError(descriptionKey = StringResourcesKey.STORE_INSERT_FAILED_ERROR_KEY)
        StoreResult.NotFound -> locale.createError(descriptionKey = StringResourcesKey.STORE_NOT_FOUND_ERROR_KEY)
        StoreResult.UpdateFailed -> locale.createError(descriptionKey = StringResourcesKey.STORE_UPDATE_FAILED_ERROR_KEY)
    }

}