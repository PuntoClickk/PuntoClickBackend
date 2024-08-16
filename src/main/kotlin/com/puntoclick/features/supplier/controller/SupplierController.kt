package com.puntoclick.features.supplier.controller

import com.puntoclick.data.database.permission.daofacade.PermissionDaoFacade
import com.puntoclick.data.database.supplier.daofacade.SupplierDaoFacade
import com.puntoclick.data.model.AppResult
import com.puntoclick.data.model.action.ActionType
import com.puntoclick.data.model.module.ModuleType
import com.puntoclick.data.model.supplier.CreateSupplierRequest
import com.puntoclick.data.model.supplier.SupplierResponse
import com.puntoclick.data.model.supplier.SupplierResult
import com.puntoclick.data.model.supplier.UpdateSupplierRequest
import com.puntoclick.features.utils.StringResourcesKey
import com.puntoclick.features.utils.createError
import com.puntoclick.features.utils.getString
import io.ktor.http.*
import java.util.Locale
import java.util.UUID

class SupplierController(
    private val supplierDaoFacade: SupplierDaoFacade,
    private val permissionDaoFacade: PermissionDaoFacade
) {
    private val module = ModuleType.PRODUCTS

    suspend fun allSuppliers(locale: Locale, roleId: UUID, teamId: UUID): AppResult<List<SupplierResponse>> {
        val permissionValidation = permissionDaoFacade.hasPermission(roleId, ActionType.READ, module, teamId)
        return if (permissionValidation) {
            val suppliers = supplierDaoFacade.allSupplier(teamId)
            return AppResult.Success(data = suppliers, appStatus = HttpStatusCode.OK)
        } else locale.createError(descriptionKey = StringResourcesKey.ACTION_PERMISSION_DENIED_ERROR_KEY)
    }

    suspend fun getSupplier(locale: Locale, supplierId: UUID, roleId: UUID, teamId: UUID): AppResult<SupplierResponse> {
        val permissionValidation = permissionDaoFacade.hasPermission(roleId, ActionType.READ, module, teamId)
        return if (permissionValidation) {
            val supplier = supplierDaoFacade.getSupplier(supplierId, teamId)
            supplier?.let {
                AppResult.Success(data = it, appStatus = HttpStatusCode.OK)
            } ?: locale.createError(descriptionKey = StringResourcesKey.SUPPLIER_NOT_FOUND_ERROR_KEY)
        } else locale.createError(descriptionKey = StringResourcesKey.ACTION_PERMISSION_DENIED_ERROR_KEY)
    }

    suspend fun addSupplier(locale: Locale, createSupplierRequest: CreateSupplierRequest, roleId: UUID, teamId: UUID): AppResult<String> {
        val permissionValidation = permissionDaoFacade.hasPermission(roleId, ActionType.READ, module, teamId)
        return if (permissionValidation) {
            val result = if (
                supplierDaoFacade.supplierExists(
                    createSupplierRequest.name,
                    createSupplierRequest.email,
                    createSupplierRequest.phoneNumber,
                    teamId)
                ) SupplierResult.AlreadyExists
            else supplierDaoFacade.addSupplier(createSupplierRequest, teamId)

            handleSupplierResult(locale, result)

        } else locale.createError(descriptionKey = StringResourcesKey.ACTION_PERMISSION_DENIED_ERROR_KEY)
    }

    suspend fun updateSupplier(locale: Locale, updateSupplierRequest: UpdateSupplierRequest, roleId: UUID, teamId: UUID): AppResult<String> {
        val permissionValidation = permissionDaoFacade.hasPermission(roleId, ActionType.READ, module, teamId)
        return if (permissionValidation) {
            val updateResult = if (
                supplierDaoFacade.supplierExists(
                    updateSupplierRequest.name,
                    updateSupplierRequest.email,
                    updateSupplierRequest.phoneNumber,
                    teamId)
                ) SupplierResult.AlreadyExists
            else supplierDaoFacade.updateSupplier(updateSupplierRequest, teamId)

            handleSupplierResult(locale, updateResult)

        } else locale.createError(descriptionKey = StringResourcesKey.ACTION_PERMISSION_DENIED_ERROR_KEY)
    }

    suspend fun deleteSupplier(locale: Locale, supplierId: UUID, roleId: UUID, teamId: UUID): AppResult<String> {
        val permissionValidation = permissionDaoFacade.hasPermission(roleId, ActionType.READ, module, teamId)
        return if (permissionValidation) {
            val deleteResult = if (!validateIfSupplierAlreadyExists(supplierDaoFacade.allSupplier(teamId), supplierId)) SupplierResult.DeleteFailed
            else supplierDaoFacade.deleteSupplier(supplierId, teamId)

            handleSupplierResult(locale, deleteResult)

        } else locale.createError(descriptionKey = StringResourcesKey.ACTION_PERMISSION_DENIED_ERROR_KEY)
    }

    private fun validateIfSupplierAlreadyExists(suppliers : List<SupplierResponse>, supplierId: UUID): Boolean {
        return suppliers.any { it.id == supplierId }
    }

    private fun handleSupplierResult(
        locale: Locale,
        result: SupplierResult,
    ): AppResult<String> {
        return when (result) {
            SupplierResult.Success -> AppResult.Success(
                data = locale.getString(StringResourcesKey.SUPPLIER_OPERATION_SUCCESS_MESSAGE_KEY),
                appStatus = HttpStatusCode.OK
            )
            SupplierResult.AlreadyExists -> locale.createError(descriptionKey = StringResourcesKey.SUPPLIER_ALREADY_EXISTS_ERROR_KEY)
            SupplierResult.InsertFailed -> locale.createError(descriptionKey = StringResourcesKey.SUPPLIER_INSERT_FAILED_ERROR_KEY)
            SupplierResult.UpdateFailed -> locale.createError(descriptionKey = StringResourcesKey.SUPPLIER_UPDATE_FAILED_ERROR_KEY)
            SupplierResult.DeleteFailed -> locale.createError(descriptionKey = StringResourcesKey.SUPPLIER_DELETE_FAILED_ERROR_KEY)
            SupplierResult.NotFound -> locale.createError(descriptionKey = StringResourcesKey.SUPPLIER_NOT_FOUND_ERROR_KEY)
            SupplierResult.InvalidData -> locale.createError(descriptionKey = StringResourcesKey.SUPPLIER_INVALID_DATA_ERROR_KEY)
            SupplierResult.PermissionDenied -> locale.createError(descriptionKey = StringResourcesKey.SUPPLIER_PERMISSION_DENIED_ERROR_KEY)
            SupplierResult.OperationFailed -> locale.createError(descriptionKey = StringResourcesKey.SUPPLIER_OPERATION_FAILED_ERROR_KEY)
        }
    }
}