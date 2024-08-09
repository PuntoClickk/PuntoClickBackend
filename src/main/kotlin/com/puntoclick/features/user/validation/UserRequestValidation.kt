package com.puntoclick.features.user.validation

import com.puntoclick.data.model.GlobalLocale
import com.puntoclick.data.model.auth.CreateAdminRequest
import com.puntoclick.data.model.auth.CreateUserRequest
import com.puntoclick.features.utils.*
import io.ktor.server.plugins.requestvalidation.*


fun CreateAdminRequest.validateCreateUserRequest(): ValidationResult {
    val locale = GlobalLocale.locale
    return when {
        !lastName.validateStringRequest() -> ValidationResult.Invalid(locale.getString(StringResourcesKey.INVALID_LAST_NAME_ERROR_KEY))
        !name.validateStringRequest() -> ValidationResult.Invalid(locale.getString(StringResourcesKey.INVALID_NAME_ERROR_KEY))
        !isValidEmail(email) -> ValidationResult.Invalid(locale.getString(StringResourcesKey.INVALID_EMAIL_ERROR_KEY))
        !isValidCellPhoneNumber(phoneNumber) -> ValidationResult.Invalid(locale.getString(StringResourcesKey.INVALID_PHONE_ERROR_KEY))
        password.isEmpty() -> ValidationResult.Invalid(locale.getString(StringResourcesKey.INVALID_PASSWORD_ERROR_KEY))
        else -> ValidationResult.Valid
    }
}

fun CreateUserRequest.validateCreateUserRequest(): ValidationResult {
    val locale = GlobalLocale.locale
    return when {
        !lastName.validateStringRequest() -> ValidationResult.Invalid(locale.getString(StringResourcesKey.INVALID_LAST_NAME_ERROR_KEY))
        !name.validateStringRequest() -> ValidationResult.Invalid(locale.getString(StringResourcesKey.INVALID_NAME_ERROR_KEY))
        !isValidEmail(email) -> ValidationResult.Invalid(locale.getString(StringResourcesKey.INVALID_EMAIL_ERROR_KEY))
        !isValidCellPhoneNumber(phoneNumber) -> ValidationResult.Invalid(locale.getString(StringResourcesKey.INVALID_PHONE_ERROR_KEY))
        password.isEmpty() -> ValidationResult.Invalid(locale.getString(StringResourcesKey.INVALID_PASSWORD_ERROR_KEY))
        else -> ValidationResult.Valid
    }
}
