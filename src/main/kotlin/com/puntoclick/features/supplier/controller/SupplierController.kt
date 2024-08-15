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

    suspend fun addSupplier(locale: Locale, createSupplierRequest: CreateSupplierRequest, userId: UUID, roleId: UUID, teamId: UUID): AppResult<String> {
        val permissionValidation = permissionDaoFacade.hasPermission(roleId, ActionType.READ, module, teamId)
        return if (permissionValidation) {
            val result = if (
                validateIfSupplierItsDuplicate(
                    supplierDaoFacade.allSupplier(teamId),
                    createSupplierRequest.name,
                    createSupplierRequest.email,
                    createSupplierRequest.phoneNumber)
                ) SupplierResult.AlreadyExists
            else supplierDaoFacade.addSupplier(createSupplierRequest, userId, teamId)

            handleSupplierResult(locale, result, StringResourcesKey.SUPPLIER_OPERATION_SUCCESS_MESSAGE_KEY)

        } else locale.createError(descriptionKey = StringResourcesKey.ACTION_PERMISSION_DENIED_ERROR_KEY)
    }

    suspend fun updateSupplier(locale: Locale, updateSupplierRequest: UpdateSupplierRequest, roleId: UUID, teamId: UUID): AppResult<String> {
        val permissionValidation = permissionDaoFacade.hasPermission(roleId, ActionType.READ, module, teamId)
        return if (permissionValidation) {
            val updateResult = if (
                validateIfSupplierItsDuplicate(
                    supplierDaoFacade.allSupplier(teamId),
                    updateSupplierRequest.name,
                    updateSupplierRequest.email,
                    updateSupplierRequest.phoneNumber)
                ) SupplierResult.AlreadyExists
            else supplierDaoFacade.updateSupplier(updateSupplierRequest, teamId)

            handleSupplierResult(locale, updateResult, StringResourcesKey.SUPPLIER_OPERATION_SUCCESS_MESSAGE_KEY)

        } else locale.createError(descriptionKey = StringResourcesKey.ACTION_PERMISSION_DENIED_ERROR_KEY)
    }

    suspend fun deleteSupplier(locale: Locale, supplierId: UUID, roleId: UUID, teamId: UUID): AppResult<String> {
        val permissionValidation = permissionDaoFacade.hasPermission(roleId, ActionType.READ, module, teamId)
        return if (permissionValidation) {
            val deleteResult = if (!validateIfSupplierAlreadyExists(supplierDaoFacade.allSupplier(teamId), supplierId)) SupplierResult.DeleteFailed
            else supplierDaoFacade.deleteSupplier(supplierId, teamId)

            handleSupplierResult(locale, deleteResult, StringResourcesKey.SUPPLIER_OPERATION_SUCCESS_MESSAGE_KEY)

        } else locale.createError(descriptionKey = StringResourcesKey.ACTION_PERMISSION_DENIED_ERROR_KEY)
    }

    private fun validateIfSupplierItsDuplicate(suppliers : List<SupplierResponse>, name: String, email: String, phoneNumber: String): Boolean {
        return suppliers.any {
            it.name == name &&
            it.email == email &&
            it.phoneNumber == phoneNumber
        }
    }

    private fun validateIfSupplierAlreadyExists(suppliers : List<SupplierResponse>, supplierId: UUID): Boolean {
        return suppliers.any { it.id == supplierId }
    }

    private fun handleSupplierResult(
        locale: Locale,
        result: SupplierResult,
        successKey: StringResourcesKey
    ): AppResult<String> {
        return when (result) {
            SupplierResult.Success -> AppResult.Success(
                data = locale.getString(successKey),
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