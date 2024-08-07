package com.puntoclick.features.category.validation

import com.puntoclick.data.model.GlobalLocale
import com.puntoclick.data.model.category.CreateCategoryRequest
import com.puntoclick.data.model.category.UpdateCategoryRequest
import com.puntoclick.features.utils.StringResourcesKey
import com.puntoclick.features.utils.getString
import com.puntoclick.features.utils.validateStringRequest
import io.ktor.server.plugins.requestvalidation.*

fun CreateCategoryRequest.validateCreateCategoryRequest (): ValidationResult {
    val locale = GlobalLocale.locale
    return when {
        !name.validateStringRequest(11) -> ValidationResult.Invalid(locale.getString(StringResourcesKey.VALID_CATEGORY_NAME_ERROR_KEY))
        else -> ValidationResult.Valid
    }
}

fun UpdateCategoryRequest.validateCreateCategoryRequest (): ValidationResult {
    val locale = GlobalLocale.locale
    return when {
        !name.validateStringRequest(11) -> ValidationResult.Invalid(locale.getString(StringResourcesKey.VALID_CATEGORY_NAME_ERROR_KEY))
        else -> ValidationResult.Valid
    }
}
