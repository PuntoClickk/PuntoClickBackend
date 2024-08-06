package com.puntoclick.data.model.permission

sealed class UpdatePermissionResult {
    data object Success : UpdatePermissionResult()
    data object AlreadyExists : UpdatePermissionResult()
    data object UserNotAdmin : UpdatePermissionResult()
    data object UpdateFailed : UpdatePermissionResult()
}