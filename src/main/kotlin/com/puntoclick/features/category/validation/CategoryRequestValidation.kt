package com.puntoclick.features.category.validation

import com.puntoclick.data.model.category.CreateCategoryRequest
import com.puntoclick.data.model.category.UpdateCategoryRequest
import com.puntoclick.features.utils.StringResourcesKey
import com.puntoclick.features.utils.getString
import com.puntoclick.features.utils.validateStringRequest
import io.ktor.server.plugins.requestvalidation.*

fun CreateCategoryRequest.validateCreateCategoryRequest (): ValidationResult {
    return when {
        !name.validateStringRequest(11) -> ValidationResult.Invalid(StringResourcesKey.VALID_CATEGORY_NAME_ERROR_KEY.getString())
        else -> ValidationResult.Valid
    }
}

fun UpdateCategoryRequest.validateCreateCategoryRequest (): ValidationResult {
    return when {
        !name.validateStringRequest(11) -> ValidationResult.Invalid(StringResourcesKey.VALID_CATEGORY_NAME_ERROR_KEY.getString())
        else -> ValidationResult.Valid
    }
}
