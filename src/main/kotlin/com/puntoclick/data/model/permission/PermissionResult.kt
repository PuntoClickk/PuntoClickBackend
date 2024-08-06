package com.puntoclick.data.model.permission

sealed class AddPermissionResult {
    data object Success : AddPermissionResult()
    data object AlreadyExists : AddPermissionResult()
    data object UserNotAdmin : AddPermissionResult()
    data object InsertFailed : AddPermissionResult()
}