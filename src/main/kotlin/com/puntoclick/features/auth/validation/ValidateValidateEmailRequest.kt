package com.puntoclick.features.auth.validation

import com.puntoclick.data.model.GlobalLocale
import com.puntoclick.data.model.auth.ValidateEmailRequest
import com.puntoclick.features.utils.StringResourcesKey
import com.puntoclick.features.utils.getString
import com.puntoclick.features.utils.isValidEmail
import io.ktor.server.plugins.requestvalidation.*

fun ValidateEmailRequest.validateEmailRequest (): ValidationResult {
    return when {
        !isValidEmail(email) -> ValidationResult.Invalid(GlobalLocale.locale.getString(StringResourcesKey.INVALID_EMAIL_ERROR_KEY))
        else -> ValidationResult.Valid
    }
}
