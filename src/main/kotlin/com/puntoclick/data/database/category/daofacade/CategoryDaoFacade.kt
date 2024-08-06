package com.puntoclick.data.database.category.daofacade

import com.puntoclick.data.model.category.CategoryResponse
import com.puntoclick.data.model.category.CreateCategoryRequest
import com.puntoclick.data.model.category.UpdateCategoryRequest
import java.util.UUID

interface CategoryDaoFacade {
    suspend fun allCategories(): List<CategoryResponse>
    suspend fun getCategory(uuid: UUID): CategoryResponse?
    suspend fun addCategory(createCategoryRequest: CreateCategoryRequest, id: UUID): Boolean
    suspend fun updateUpdateCategory(updateCategoryRequest: UpdateCategoryRequest): Boolean
    suspend fun deleteCategory(uuid: UUID): Boolean
}