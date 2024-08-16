package com.puntoclick.data.database.supplier.daofacade

import com.puntoclick.data.model.supplier.CreateSupplierRequest
import com.puntoclick.data.model.supplier.SupplierResponse
import com.puntoclick.data.model.supplier.SupplierResult
import com.puntoclick.data.model.supplier.UpdateSupplierRequest
import java.util.UUID

interface SupplierDaoFacade {
    suspend fun allSupplier(teamId: UUID): List<SupplierResponse>
    suspend fun getSupplier(supplierId: UUID, teamId: UUID): SupplierResponse?
    suspend fun addSupplier(createSupplierRequest: CreateSupplierRequest, teamId: UUID): SupplierResult
    suspend fun updateSupplier(updateSupplierRequest: UpdateSupplierRequest, teamId: UUID): SupplierResult
    suspend fun supplierExists(name: String, email: String, phoneNumber: String, teamId: UUID): Boolean
    suspend fun deleteSupplier(supplierID: UUID, teamId: UUID): SupplierResult
}