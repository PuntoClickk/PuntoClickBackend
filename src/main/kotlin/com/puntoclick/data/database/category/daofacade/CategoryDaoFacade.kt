package com.puntoclick.data.database.category.daofacade

import com.puntoclick.data.model.category.CategoryResponse
import com.puntoclick.data.model.category.CategoryResult
import com.puntoclick.data.model.category.CreateCategoryRequest
import com.puntoclick.data.model.category.UpdateCategoryRequest
import java.util.UUID

interface CategoryDaoFacade {
    suspend fun allCategories(teamId: UUID): List<CategoryResponse>
    suspend fun getCategory(uuid: UUID, teamId: UUID): CategoryResponse?
    suspend fun addCategory(createCategoryRequest: CreateCategoryRequest, id: UUID, teamId: UUID): CategoryResult
    suspend fun updateUpdateCategory(updateCategoryRequest: UpdateCategoryRequest, teamId: UUID): CategoryResult
    suspend fun deleteCategory(uuid: UUID): CategoryResult
}