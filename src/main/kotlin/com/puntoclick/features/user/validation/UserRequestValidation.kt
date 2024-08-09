package com.puntoclick.features.user.validation

import com.puntoclick.data.model.auth.CreateAdminRequest
import com.puntoclick.data.model.auth.CreateUserRequest
import com.puntoclick.features.utils.*
import io.ktor.server.plugins.requestvalidation.*


fun CreateAdminRequest.validateCreateUserRequest(): ValidationResult {
    return when {
        !lastName.validateStringRequest() -> ValidationResult.Invalid(StringResourcesKey.INVALID_LAST_NAME_ERROR_KEY.getString())
        !name.validateStringRequest() -> ValidationResult.Invalid(StringResourcesKey.INVALID_NAME_ERROR_KEY.getString())
        !isValidEmail(email) -> ValidationResult.Invalid(StringResourcesKey.INVALID_EMAIL_ERROR_KEY.getString())
        !isValidCellPhoneNumber(phoneNumber) -> ValidationResult.Invalid(StringResourcesKey.INVALID_PHONE_ERROR_KEY.getString())
        password.isEmpty() -> ValidationResult.Invalid(StringResourcesKey.INVALID_PASSWORD_ERROR_KEY.getString())
        else -> ValidationResult.Valid
    }
}

fun CreateUserRequest.validateCreateUserRequest(): ValidationResult {
    return when {
        !lastName.validateStringRequest() -> ValidationResult.Invalid(StringResourcesKey.INVALID_LAST_NAME_ERROR_KEY.getString())
        !name.validateStringRequest() -> ValidationResult.Invalid(StringResourcesKey.INVALID_NAME_ERROR_KEY.getString())
        !isValidEmail(email) -> ValidationResult.Invalid(StringResourcesKey.INVALID_EMAIL_ERROR_KEY.getString())
        !isValidCellPhoneNumber(phoneNumber) -> ValidationResult.Invalid(StringResourcesKey.INVALID_PHONE_ERROR_KEY.getString())
        password.isEmpty() -> ValidationResult.Invalid(StringResourcesKey.INVALID_PASSWORD_ERROR_KEY.getString())
        else -> ValidationResult.Valid
    }
}
