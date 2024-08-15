package com.puntoclick.features.category.controller

import com.puntoclick.data.database.category.daofacade.CategoryDaoFacade
import com.puntoclick.data.database.permission.daofacade.PermissionDaoFacade
import com.puntoclick.data.model.AppResult
import com.puntoclick.data.model.action.ActionType
import com.puntoclick.data.model.category.CategoryResponse
import com.puntoclick.data.model.category.CategoryResult
import com.puntoclick.data.model.category.CreateCategoryRequest
import com.puntoclick.data.model.category.UpdateCategoryRequest
import com.puntoclick.data.model.module.ModuleType
import com.puntoclick.data.model.supplier.SupplierResult
import com.puntoclick.features.utils.StringResourcesKey
import com.puntoclick.features.utils.createError
import com.puntoclick.features.utils.createGenericError
import com.puntoclick.features.utils.getString
import io.ktor.http.*
import java.util.*

class CategoryController(
    private val categoryFacade: CategoryDaoFacade,
    private val permissionDaoFacade: PermissionDaoFacade
) {

    private val module = ModuleType.PRODUCTS

    suspend fun allCategories(locale: Locale, roleID: UUID, teamId: UUID): AppResult<List<CategoryResponse>> {
        val userAllowed = permissionDaoFacade.hasPermission(
            roleID,
            ActionType.READ,
            module,
            teamId
        )
        if (!userAllowed) return locale.createError(descriptionKey = StringResourcesKey.FEATURE_ACCESS_DENIED_ERROR_KEY)

        val categories = categoryFacade.allCategories(teamId)
        return AppResult.Success(data = categories, appStatus = HttpStatusCode.OK)
    }

    suspend fun getCategory(locale: Locale, categoryId: UUID, roleID: UUID, teamId: UUID): AppResult<CategoryResponse> {
        val userAllowed = permissionDaoFacade.hasPermission(
            roleID,
            ActionType.READ,
            module,
            teamId
        )
        if (!userAllowed) return locale.createError(descriptionKey = StringResourcesKey.FEATURE_ACCESS_DENIED_ERROR_KEY)

        val category = categoryFacade.getCategory(categoryId, teamId)
        return category?.let {
                AppResult.Success(data = it, appStatus = HttpStatusCode.OK)
        } ?: locale.createError(descriptionKey = StringResourcesKey.CATEGORY_NOT_FOUND_ERROR_KEY)
    }

    suspend fun addCategory(locale: Locale, createCategoryRequest: CreateCategoryRequest, userId: UUID, roleID: UUID, teamId: UUID): AppResult<String> {
        val userAllowed = permissionDaoFacade.hasPermission(
            roleID,
            ActionType.WRITE,
            module,
            teamId
        )
        if (!userAllowed) return locale.createError(descriptionKey = StringResourcesKey.FEATURE_ACCESS_DENIED_ERROR_KEY)

        val result = if (categoryFacade.categoryExists(createCategoryRequest.name, teamId)) CategoryResult.AlreadyExists
        else categoryFacade.addCategory(createCategoryRequest, userId, teamId)
        return handleCategoryResult(locale, result, StringResourcesKey.SUPPLIER_OPERATION_SUCCESS_MESSAGE_KEY)
    }

    suspend fun updateCategory(locale: Locale, updateCategoryRequest: UpdateCategoryRequest, roleID: UUID, teamId: UUID): AppResult<String> {
        val userAllowed = permissionDaoFacade.hasPermission(
            roleID,
            ActionType.UPDATE,
            module,
            teamId
        )
        if (!userAllowed) return locale.createError(descriptionKey = StringResourcesKey.FEATURE_ACCESS_DENIED_ERROR_KEY)

        val updateResult = if (categoryFacade.categoryExists(updateCategoryRequest.name, teamId)) CategoryResult.AlreadyExists
        else categoryFacade.updateCategory(updateCategoryRequest, teamId)
        return handleCategoryResult(locale, updateResult, StringResourcesKey.CATEGORY_OPERATION_SUCCESS_MESSAGE_KEY)
    }

    suspend fun deleteCategory(locale: Locale, categoryId: UUID, roleID: UUID, teamId: UUID): AppResult<String> {
        val userAllowed = permissionDaoFacade.hasPermission(
            roleID,
            ActionType.DELETE,
            module,
            teamId
        )
        if (!userAllowed) return locale.createError(descriptionKey = StringResourcesKey.FEATURE_ACCESS_DENIED_ERROR_KEY)

        val deleteResult = if (!doesCategoryExist(categoryFacade.allCategories(teamId), categoryId)) CategoryResult.DeleteFailed
        else categoryFacade.deleteCategory(categoryId, teamId)
        return handleCategoryResult(locale, deleteResult, StringResourcesKey.CATEGORY_OPERATION_SUCCESS_MESSAGE_KEY)
    }

    private fun doesCategoryExist(categories: List<CategoryResponse>, categoryId: UUID): Boolean {
        return categories.any { it.id == categoryId }
    }

    private fun handleCategoryResult(
        locale: Locale,
        result: CategoryResult,
        successKey: StringResourcesKey
    ): AppResult<String> {
        return when (result) {
            CategoryResult.Success -> AppResult.Success(
                data = locale.getString(successKey),
                appStatus = HttpStatusCode.OK
            )
            CategoryResult.AlreadyExists -> locale.createError(descriptionKey = StringResourcesKey.CATEGORY_ALREADY_EXISTS_ERROR_KEY)
            CategoryResult.InsertFailed -> locale.createError(descriptionKey = StringResourcesKey.CATEGORY_INSERT_FAILED_ERROR_KEY)
            CategoryResult.UpdateFailed -> locale.createError(descriptionKey = StringResourcesKey.CATEGORY_UPDATE_FAILED_ERROR_KEY)
            CategoryResult.DeleteFailed -> locale.createError(descriptionKey = StringResourcesKey.CATEGORY_DELETE_FAILED_ERROR_KEY)
            CategoryResult.NotFound -> locale.createError(descriptionKey = StringResourcesKey.CATEGORY_NOT_FOUND_ERROR_KEY)
            CategoryResult.InvalidData -> locale.createError(descriptionKey = StringResourcesKey.CATEGORY_INVALID_DATA_ERROR_KEY)
            CategoryResult.PermissionDenied -> locale.createError(descriptionKey = StringResourcesKey.CATEGORY_PERMISSION_DENIED_ERROR_KEY)
            CategoryResult.OperationFailed -> locale.createError(descriptionKey = StringResourcesKey.CATEGORY_OPERATION_FAILED_ERROR_KEY)
        }
    }
}