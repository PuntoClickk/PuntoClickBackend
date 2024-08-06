package com.puntoclick.data.database.category.daofacade

import com.puntoclick.data.database.category.table.CategoriesTable
import com.puntoclick.data.database.dbQuery
import com.puntoclick.data.model.category.CategoryResponse
import com.puntoclick.data.model.category.CreateCategoryRequest
import com.puntoclick.data.model.category.UpdateCategoryRequest
import com.puntoclick.features.utils.escapeSingleQuotes
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.*

class CategoryDaoFacadeImp: CategoryDaoFacade {
    override suspend fun allCategories(): List<CategoryResponse> = dbQuery {
        CategoriesTable.selectAll().map(::resultRowToCategory)
    }

    override suspend fun getCategory(uuid: UUID): CategoryResponse? = dbQuery  {
        CategoriesTable.select {
            CategoriesTable.uuid eq uuid
        }.map(::resultRowToCategory).singleOrNull()
    }

    override suspend fun addCategory(createCategoryRequest: CreateCategoryRequest, id: UUID): Boolean = dbQuery  {
        CategoriesTable.insert {
            it[name] = createCategoryRequest.name.escapeSingleQuotes()
            it[user] = id
        }.resultedValues?.singleOrNull() != null
    }

    override suspend fun updateUpdateCategory(updateCategoryRequest: UpdateCategoryRequest): Boolean = dbQuery  {
        CategoriesTable.update({
            CategoriesTable.uuid eq updateCategoryRequest.id
        }) {
            it[name] = updateCategoryRequest.name.escapeSingleQuotes()
        } > 0
    }

    override suspend fun deleteCategory(uuid: UUID): Boolean = dbQuery  {
        CategoriesTable.deleteWhere { CategoriesTable.uuid eq uuid } > 0
    }

    private fun resultRowToCategory(row: ResultRow) = CategoryResponse(
        id = row[CategoriesTable.uuid],
        name = row[CategoriesTable.name]
    )
}