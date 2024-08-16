package com.puntoclick.data.database.supplier.daofacade

import com.puntoclick.data.database.category.table.CategoriesTable.updatedAt
import com.puntoclick.data.database.dbQuery
import com.puntoclick.data.database.supplier.table.SupplierTable
import com.puntoclick.data.model.supplier.CreateSupplierRequest
import com.puntoclick.data.model.supplier.SupplierResponse
import com.puntoclick.data.model.supplier.SupplierResult
import com.puntoclick.data.model.supplier.UpdateSupplierRequest
import com.puntoclick.features.utils.escapeSingleQuotes
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.*

class SupplierDaoFacadeImp: SupplierDaoFacade {

    override suspend fun allSupplier(teamId: UUID): List<SupplierResponse> = dbQuery {
        SupplierTable.selectAll().where { SupplierTable.team eq teamId }.map(::resultRowToSupplier)
    }

    override suspend fun getSupplier(supplierId: UUID, teamId: UUID): SupplierResponse? = dbQuery {
        SupplierTable.selectAll().where { (SupplierTable.uuid eq supplierId) and (SupplierTable.team eq teamId) }.map(::resultRowToSupplier).singleOrNull()
    }

    override suspend fun addSupplier(
        createSupplierRequest: CreateSupplierRequest,
        teamId: UUID
    ): SupplierResult = dbQuery {
        val insertResult = SupplierTable.insert {
            it[companyName] = createSupplierRequest.company.escapeSingleQuotes()
            it[name] = createSupplierRequest.name.escapeSingleQuotes()
            it[email] = createSupplierRequest.email.escapeSingleQuotes()
            it[phoneNumber] = createSupplierRequest.phoneNumber
            it[team] = teamId
        }

        if (insertResult.insertedCount > 0) SupplierResult.Success
        else SupplierResult.InsertFailed
    }

    override suspend fun updateSupplier(updateSupplierRequest: UpdateSupplierRequest, teamId: UUID): SupplierResult = dbQuery {
        val updateResult = SupplierTable.update({
            (SupplierTable.uuid eq updateSupplierRequest.id) and (SupplierTable.team eq teamId)
        }) {
            it[companyName] = updateSupplierRequest.company.escapeSingleQuotes()
            it[name] = updateSupplierRequest.name.escapeSingleQuotes()
            it[email] = updateSupplierRequest.email.escapeSingleQuotes()
            it[phoneNumber] = updateSupplierRequest.phoneNumber
            it[team] = teamId
            it[updatedAt] = System.currentTimeMillis()
        }

        if (updateResult > 0) SupplierResult.Success
        else SupplierResult.InsertFailed
    }

    override suspend fun supplierExists(name: String, email: String, phoneNumber: String, teamId: UUID) : Boolean = dbQuery {
        val exists = SupplierTable.selectAll().where {
            (SupplierTable.name eq name) and
                    (SupplierTable.email eq email) and
                    (SupplierTable.phoneNumber eq phoneNumber) and
                    (SupplierTable.team eq teamId)
        }.singleOrNull() != null
        exists
    }

    override suspend fun deleteSupplier(supplierID: UUID, teamId: UUID): SupplierResult = dbQuery {
        val deleteResult = SupplierTable.deleteWhere {
            (uuid eq supplierID) and (team eq teamId)
        }

        if (deleteResult > 0) SupplierResult.Success
        else SupplierResult.DeleteFailed
    }

    private fun resultRowToSupplier(row: ResultRow) = SupplierResponse(
        id = row[SupplierTable.uuid],
        companyName = row[SupplierTable.companyName],
        name = row[SupplierTable.name],
        email = row[SupplierTable.email],
        phoneNumber = row[SupplierTable.phoneNumber]
    )
}