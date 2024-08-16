package com.puntoclick.data.database.category.daofacade

import com.puntoclick.data.database.category.table.CategoriesTable
import com.puntoclick.data.database.dbQuery
import com.puntoclick.data.model.category.CategoryResponse
import com.puntoclick.data.model.category.CategoryResult
import com.puntoclick.data.model.category.CreateCategoryRequest
import com.puntoclick.data.model.category.UpdateCategoryRequest
import com.puntoclick.features.utils.escapeSingleQuotes
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.*

class CategoryDaoFacadeImp: CategoryDaoFacade {
    override suspend fun allCategories(teamId: UUID): List<CategoryResponse> = dbQuery {
        CategoriesTable.select { CategoriesTable.team eq teamId }.map(::resultRowToCategory)
    }

    override suspend fun getCategory(uuid: UUID, teamId: UUID): CategoryResponse? = dbQuery  {
        CategoriesTable.select {
            (CategoriesTable.uuid eq uuid) and (CategoriesTable.team eq teamId)
        }.map(::resultRowToCategory).singleOrNull()
    }

    override suspend fun addCategory(
        createCategoryRequest: CreateCategoryRequest,
        userId: UUID,
        teamId: UUID
    ): CategoryResult = dbQuery {

        val insertResult = CategoriesTable.insert {
            it[name] = createCategoryRequest.name.escapeSingleQuotes()
            it[user] = userId
            it[team] = teamId
        }

        if (insertResult.insertedCount > 0 ) CategoryResult.Success
        else CategoryResult.InsertFailed
    }

    override suspend fun updateCategory(updateCategoryRequest: UpdateCategoryRequest, teamId: UUID): CategoryResult = dbQuery  {

        val updateResult = CategoriesTable.update({
            (CategoriesTable.uuid eq updateCategoryRequest.id) and (CategoriesTable.team eq teamId)
        }) {
            it[name] = updateCategoryRequest.name.escapeSingleQuotes()
            it[updatedAt] = System.currentTimeMillis()
        }

        if (updateResult > 0) CategoryResult.Success
        else CategoryResult.UpdateFailed
    }

    override suspend fun categoryExists(name: String, teamId: UUID): Boolean = dbQuery {
        val exists = CategoriesTable.select {
            (CategoriesTable.name eq name) and (CategoriesTable.team eq teamId)
        }.singleOrNull() != null
        exists
    }

    override suspend fun deleteCategory(uuid: UUID, teamId: UUID): CategoryResult = dbQuery  {
        val deleteResult = CategoriesTable.deleteWhere {
            (CategoriesTable.uuid eq uuid) and (CategoriesTable.team eq teamId)
        }

        if (deleteResult > 0) CategoryResult.Success
        else CategoryResult.DeleteFailed
    }

    private fun resultRowToCategory(row: ResultRow) = CategoryResponse(
        id = row[CategoriesTable.uuid],
        name = row[CategoriesTable.name]
    )
}