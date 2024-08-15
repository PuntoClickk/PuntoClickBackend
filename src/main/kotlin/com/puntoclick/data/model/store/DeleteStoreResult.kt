package com.puntoclick.data.model.store

sealed class DeleteStoreResult {
    data object Success : DeleteStoreResult()
    data object DeleteFailed : DeleteStoreResult()
    data object NotFound : DeleteStoreResult()
}
