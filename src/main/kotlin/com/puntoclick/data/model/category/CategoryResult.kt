package com.puntoclick.data.model.category

sealed class CategoryResult {
    data object Success : CategoryResult()
    data object AlreadyExists : CategoryResult()
    data object InsertFailed : CategoryResult()
    data object DeleteFailed : CategoryResult()
}