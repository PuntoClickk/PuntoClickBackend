package com.puntoclick.features.category.controller

import com.puntoclick.data.database.category.daofacade.CategoryDaoFacade
import com.puntoclick.data.model.AppResult
import com.puntoclick.data.model.category.CategoryResponse
import com.puntoclick.data.model.category.CreateCategoryRequest
import com.puntoclick.data.model.category.UpdateCategoryRequest
import com.puntoclick.features.utils.createError
import com.puntoclick.features.utils.createGenericError
import io.ktor.http.*
import java.util.*

class CategoryController(
    private val facade: CategoryDaoFacade
) {
    suspend fun allCategories(): AppResult<List<CategoryResponse>> {
        val categories = facade.allCategories()
        return AppResult.Success(data = categories, appStatus = HttpStatusCode.OK)
    }

    suspend fun getCategory(uuid: UUID, locale: Locale): AppResult<CategoryResponse> {
        val category = facade.getCategory(uuid)
        return category?.let {
            AppResult.Success(data = it, appStatus = HttpStatusCode.OK)
        } ?: locale.createGenericError()
    }

    suspend fun addCategory(createCategoryRequest: CreateCategoryRequest, uuid: UUID, locale: Locale): AppResult<Boolean> {
        return createCategory(createCategoryRequest, uuid, locale)
    }

    suspend fun updateCategory(updateCategoryRequest: UpdateCategoryRequest, locale: Locale): AppResult<Boolean> {
        return updateCategoryName(updateCategoryRequest, locale)
    }

    suspend fun deleteCategory(uuid: UUID, locale: Locale): AppResult<Boolean> {
        return if (facade.deleteCategory(uuid)) AppResult.Success(data = true, appStatus = HttpStatusCode.OK)
        else locale.createGenericError()
    }

    private suspend fun createCategory(createCategoryRequest: CreateCategoryRequest, uuid: UUID, locale: Locale): AppResult<Boolean> =
        if (facade.addCategory(createCategoryRequest, uuid)) {
            AppResult.Success(data = true, appStatus = HttpStatusCode.OK)
        } else locale.createGenericError()

    private suspend fun updateCategoryName(updateCategoryRequest: UpdateCategoryRequest, locale: Locale): AppResult<Boolean> =
        if (facade.updateUpdateCategory(updateCategoryRequest)) {
            AppResult.Success(data = true, appStatus = HttpStatusCode.OK)
        } else locale.createGenericError()
}