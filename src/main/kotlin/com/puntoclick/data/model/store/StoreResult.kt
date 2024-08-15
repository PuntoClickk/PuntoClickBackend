package com.puntoclick.data.model.store

sealed class StoreResult {
    data object Success : StoreResult()
    data object DeleteFailed : StoreResult()
    data object NotFound : StoreResult()
    data object AlreadyExists: StoreResult()
    data object InsertFailed: StoreResult()
    data object UpdateFailed : StoreResult()
}
