package com.puntoclick.features.store.validation

import com.puntoclick.data.model.GlobalLocale
import com.puntoclick.data.model.store.CreateStoreRequest
import com.puntoclick.data.model.store.UpdateStoreRequest
import com.puntoclick.data.utils.LOCATION_LENGTH
import com.puntoclick.data.utils.NAME_LENGTH
import com.puntoclick.features.utils.StringResourcesKey
import com.puntoclick.features.utils.getString
import com.puntoclick.features.utils.validateStringRequest
import io.ktor.server.plugins.requestvalidation.*

fun CreateStoreRequest.validateCreteStoreRequest(): ValidationResult {
    val locale = GlobalLocale.locale
    return when {
        !name.validateStringRequest(NAME_LENGTH) -> ValidationResult.Invalid(locale.getString(StringResourcesKey.STORE_NAME_INVALID_ERROR_KEY))
        !location.validateStringRequest(LOCATION_LENGTH) -> ValidationResult.Invalid(locale.getString(StringResourcesKey.LOCATION_NAME_INVALID_ERROR_KEY))
        else -> ValidationResult.Valid
    }
}

fun UpdateStoreRequest.validateCreteStoreRequest(): ValidationResult {
    val locale = GlobalLocale.locale
    return when {
        !name.validateStringRequest(NAME_LENGTH) -> ValidationResult.Invalid(locale.getString(StringResourcesKey.STORE_NAME_INVALID_ERROR_KEY))
        !location.validateStringRequest(LOCATION_LENGTH) -> ValidationResult.Invalid(locale.getString(StringResourcesKey.LOCATION_NAME_INVALID_ERROR_KEY))
        else -> ValidationResult.Valid
    }
}
