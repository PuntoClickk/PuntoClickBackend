package com.puntoclick.data.model.permission

sealed class DeletePermissionResult {
    data object Success : DeletePermissionResult()
    data object PermissionNotFound : DeletePermissionResult()
    data object UserNotAdmin : DeletePermissionResult()
    data object DeleteFailed : DeletePermissionResult()
}