package com.puntoclick.features.store.controller

import com.puntoclick.data.database.permission.daofacade.PermissionDaoFacade
import com.puntoclick.data.database.store.facade.StoreDaoFacade
import com.puntoclick.data.model.AppResult
import com.puntoclick.data.model.action.ActionType
import com.puntoclick.data.model.module.ModuleType
import com.puntoclick.data.model.store.CreateStoreRequest
import com.puntoclick.data.model.store.CreateStoreResult
import com.puntoclick.data.model.store.toStoreData
import com.puntoclick.features.utils.StringResourcesKey
import com.puntoclick.features.utils.createError
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
        roleId: UUID,
        teamId: UUID
    ): AppResult<String> {
        val userAllowed = permissionDaoFacade.hasPermission(
            roleId = roleId,
            actionType = ActionType.WRITE,
            moduleType = module,
            teamId = teamId
        )

        if (userAllowed) return locale.createError()


        val result = storeDaoFacade.createStore(createStoreRequest.toStoreData())

        return when (result) {
            CreateStoreResult.AlreadyExists -> locale.createError(descriptionKey = StringResourcesKey.PERMISSION_ALREADY_EXISTS_ERROR_KEY)
            CreateStoreResult.InsertFailed -> locale.createError()
            CreateStoreResult.Success -> AppResult.Success(data = "")
        }
    }

}