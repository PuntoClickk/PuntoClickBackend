package com.puntoclick.features.roles.validation

import com.puntoclick.data.model.role.CreateRoleRequest
import com.puntoclick.features.utils.validateStringRequest
import io.ktor.server.plugins.requestvalidation.*

fun CreateRoleRequest.validateRoleRequest (): ValidationResult {
    return when {
        !name.validateStringRequest() -> ValidationResult.Invalid("Invalid name")
        else -> ValidationResult.Valid
    }
}
