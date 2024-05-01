package com.puntoclick.features.user.validation

import com.puntoclick.data.model.user.CreateUserRequest
import com.puntoclick.features.utils.isValidCellPhoneNumber
import com.puntoclick.features.utils.isValidEmail
import com.puntoclick.features.utils.validateStringRequest
import io.ktor.server.plugins.requestvalidation.*



fun CreateUserRequest.validateCreateUserRequest (): ValidationResult {
        return when {
            !lastName.validateStringRequest() -> ValidationResult.Invalid("Invalid Name")
            !name.validateStringRequest() -> ValidationResult.Invalid("Invalid Name")
            !isValidEmail(email) -> ValidationResult.Invalid("Invalid Email")
            !isValidCellPhoneNumber(phoneNumber) -> ValidationResult.Invalid("Invalid Phone")
            password.isEmpty() -> ValidationResult.Invalid("Invalid Password")
            else -> ValidationResult.Valid
        }
}
