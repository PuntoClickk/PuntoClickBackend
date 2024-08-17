package com.puntoclick.data.model.userstoreassigment

sealed class UserStoreAssignmentResult {
    data object Success : UserStoreAssignmentResult()
    data object AssignmentFailed : UserStoreAssignmentResult()
    data object NotFound : UserStoreAssignmentResult()
    data object AlreadyAssigned : UserStoreAssignmentResult()
}

