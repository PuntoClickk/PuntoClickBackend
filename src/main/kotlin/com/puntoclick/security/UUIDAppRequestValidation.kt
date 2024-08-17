package com.puntoclick.security

import com.puntoclick.data.model.GlobalLocale
import com.puntoclick.data.model.UUIDAppRequest
import com.puntoclick.data.utils.validateUUID
import com.puntoclick.features.utils.StringResourcesKey
import com.puntoclick.features.utils.getString
import io.ktor.server.plugins.requestvalidation.*


fun UUIDAppRequest.validateUUIDAppRequest(): ValidationResult {
    val locale = GlobalLocale.locale
    return when {
        !id.validateUUID() -> ValidationResult.Invalid(locale.getString(StringResourcesKey.GENERIC_DESCRIPTION_ERROR_KEY))
        else -> ValidationResult.Valid
    }
}