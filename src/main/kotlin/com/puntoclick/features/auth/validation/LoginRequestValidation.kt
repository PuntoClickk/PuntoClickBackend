package com.puntoclick.features.auth.validation

import com.puntoclick.data.model.GlobalLocale
import com.puntoclick.data.model.auth.LoginRequest
import com.puntoclick.features.utils.StringResourcesKey
import com.puntoclick.features.utils.getString
import com.puntoclick.features.utils.isValidEmail
import io.ktor.server.plugins.requestvalidation.*

fun LoginRequest.validateLoginRequest(): ValidationResult {
    val locale = GlobalLocale.locale
    return when {
        !isValidEmail(email) -> ValidationResult.Invalid(locale.getString(StringResourcesKey.INVALID_EMAIL_ERROR_KEY))
        password.isEmpty() -> ValidationResult.Invalid(locale.getString(StringResourcesKey.INVALID_PASSWORD_ERROR_KEY))
        else -> ValidationResult.Valid
    }
}
