package com.puntoclick.data.model.store

sealed class CreateStoreResult {
    data object AlreadyExists: CreateStoreResult()
    data object InsertFailed: CreateStoreResult()
    data object Success: CreateStoreResult()
}