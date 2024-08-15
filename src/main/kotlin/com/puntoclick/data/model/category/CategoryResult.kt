package com.puntoclick.data.model.category

sealed class CategoryResult {
    data object Success : CategoryResult()
    data object AlreadyExists : CategoryResult()
    data object InsertFailed : CategoryResult()
    data object UpdateFailed : CategoryResult()
    data object DeleteFailed : CategoryResult()
    data object NotFound : CategoryResult()
    data object InvalidData : CategoryResult()
    data object PermissionDenied : CategoryResult()
    data object OperationFailed : CategoryResult()
}