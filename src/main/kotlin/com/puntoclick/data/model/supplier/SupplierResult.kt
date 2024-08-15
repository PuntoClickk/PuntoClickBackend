package com.puntoclick.data.model.supplier

sealed class SupplierResult {
    data object Success : SupplierResult()
    data object AlreadyExists : SupplierResult()
    data object InsertFailed : SupplierResult()
    data object UpdateFailed : SupplierResult()
    data object DeleteFailed : SupplierResult()
    data object NotFound : SupplierResult()
    data object InvalidData : SupplierResult()
    data object PermissionDenied : SupplierResult()
    data object OperationFailed : SupplierResult()
}