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

    suspend fun allCategories(roleID: UUID, teamId: UUID): AppResult<List<CategoryResponse>> {
        val permissionValidation = permissionDaoFacade.hasPermission(roleID, ActionType.READ, module, teamId)
        return if (permissionValidation) {
            val categories = categoryFacade.allCategories(teamId)
            return AppResult.Success(data = categories, appStatus = HttpStatusCode.OK)
        } else createError(descriptionKey = StringResourcesKey.FEATURE_ACCESS_DENIED_ERROR_KEY)
    }

    suspend fun getCategory(categoryId: UUID, roleID: UUID, teamId: UUID): AppResult<CategoryResponse> {
        val permissionValidation = permissionDaoFacade.hasPermission(roleID, ActionType.READ, module, teamId)
        return if (permissionValidation) {
            val category = categoryFacade.getCategory(categoryId, teamId)
            return category?.let {
                AppResult.Success(data = it, appStatus = HttpStatusCode.OK)
            } ?: createGenericError()
        } else createError(descriptionKey = StringResourcesKey.FEATURE_ACCESS_DENIED_ERROR_KEY)
    }

    suspend fun addCategory(createCategoryRequest: CreateCategoryRequest, userId: UUID, roleID: UUID, teamId: UUID): AppResult<String> {
        val permissionValidation = permissionDaoFacade.hasPermission(roleID, ActionType.WRITE, module, teamId)
        return if (permissionValidation) {

            val result = if (isCategoryNameDuplicate(categoryFacade.allCategories(teamId), createCategoryRequest.name)) CategoryResult.AlreadyExists
            else categoryFacade.addCategory(createCategoryRequest, userId, teamId)

            return when(result) {
                CategoryResult.AlreadyExists -> createError(descriptionKey = StringResourcesKey.CATEGORY_ADD_ALREADY_EXISTS_ERROR_KEY)
                CategoryResult.InsertFailed -> createError(descriptionKey = StringResourcesKey.CATEGORY_ADD_FAILED_ERROR_KEY)
                CategoryResult.DeleteFailed -> createError(descriptionKey = StringResourcesKey.CATEGORY_NOT_FOUND_ERROR_KEY)
                CategoryResult.Success -> AppResult.Success(
                    data = StringResourcesKey.CATEGORY_ADD_SUCCESS_MESSAGE_KEY.getString(),
                    appStatus = HttpStatusCode.OK
                )
            }
        } else createError(descriptionKey = StringResourcesKey.CATEGORY_ADD_USER_CANNOT_ADD_ERROR_KEY)
    }

    suspend fun updateCategory(updateCategoryRequest: UpdateCategoryRequest, roleID: UUID, teamId: UUID): AppResult<String> {
        val permissionValidation = permissionDaoFacade.hasPermission(roleID, ActionType.UPDATE, module, teamId)
        return if (permissionValidation) {

            val updateResult = if (isCategoryNameDuplicate(categoryFacade.allCategories(teamId), updateCategoryRequest.name)) CategoryResult.AlreadyExists
            else categoryFacade.updateUpdateCategory(updateCategoryRequest, teamId)

            return when(updateResult) {
                CategoryResult.AlreadyExists -> createError(descriptionKey = StringResourcesKey.CATEGORY_ADD_ALREADY_EXISTS_ERROR_KEY)
                CategoryResult.InsertFailed -> createError(descriptionKey = StringResourcesKey.CATEGORY_ADD_FAILED_ERROR_KEY)
                CategoryResult.DeleteFailed -> createError(descriptionKey = StringResourcesKey.CATEGORY_NOT_FOUND_ERROR_KEY)
                CategoryResult.Success -> AppResult.Success(
                    data = StringResourcesKey.CATEGORY_UPDATED_SUCCESS_MESSAGE_KEY.getString(),
                    appStatus = HttpStatusCode.OK
                )
            }
        } else createError(descriptionKey = StringResourcesKey.FEATURE_ACCESS_DENIED_ERROR_KEY)
    }

    suspend fun deleteCategory(categoryId: UUID, roleID: UUID, teamId: UUID): AppResult<String> {
        val permissionValidation = permissionDaoFacade.hasPermission(roleID, ActionType.DELETE, module, teamId)
        return if (permissionValidation) {

            val deleteResult = if (!doesCategoryExist(categoryFacade.allCategories(teamId), categoryId)) CategoryResult.DeleteFailed
            else categoryFacade.deleteCategory(categoryId, teamId)

            return when(deleteResult) {
                CategoryResult.AlreadyExists -> createError(descriptionKey = StringResourcesKey.CATEGORY_ADD_ALREADY_EXISTS_ERROR_KEY)
                CategoryResult.InsertFailed -> createError(descriptionKey = StringResourcesKey.CATEGORY_ADD_FAILED_ERROR_KEY)
                CategoryResult.DeleteFailed -> createError(descriptionKey = StringResourcesKey.CATEGORY_NOT_FOUND_ERROR_KEY)
                CategoryResult.Success -> AppResult.Success(
                    data = StringResourcesKey.CATEGORY_DELETED_SUCCESS_MESSAGE_KEY.getString(),
                    appStatus = HttpStatusCode.OK
                )
            }

        } else createError(descriptionKey = StringResourcesKey.FEATURE_ACCESS_DENIED_ERROR_KEY)
    }

    private fun isCategoryNameDuplicate(categories: List<CategoryResponse>, categoryName: String): Boolean {
        return categories.any { it.name == categoryName }
    }

    private fun doesCategoryExist(categories: List<CategoryResponse>, categoryId: UUID): Boolean {
        return categories.any { it.id == categoryId }
    }
}