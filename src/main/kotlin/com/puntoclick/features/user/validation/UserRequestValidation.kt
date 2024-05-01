package com.puntoclick.features.user.validation

import com.puntoclick.features.user.model.CreateUserRequest
import com.puntoclick.features.utils.isValidEmail
import com.puntoclick.features.utils.validateStringRequest
import io.ktor.server.plugins.requestvalidation.*



fun CreateUserRequest.validateCreateUserRequest (): ValidationResult {
        return when {
            !lastName.validateStringRequest() -> ValidationResult.Invalid("Error Name")
            !name.validateStringRequest() -> ValidationResult.Invalid("Error Name")
            isValidEmail(email) -> ValidationResult.Invalid("Error Email")
            else -> ValidationResult.Valid
        }
}