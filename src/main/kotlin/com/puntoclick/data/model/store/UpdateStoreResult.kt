package com.puntoclick.data.model.store

sealed class UpdateStoreResult {
    data object Success : UpdateStoreResult()
    data object AlreadyExists: UpdateStoreResult()
    data object UpdateFailed : UpdateStoreResult()
    data object NotFound : UpdateStoreResult()
}