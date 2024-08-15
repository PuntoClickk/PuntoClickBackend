package com.puntoclick.features.supplier.validation

import com.puntoclick.data.model.GlobalLocale
import com.puntoclick.data.model.supplier.CreateSupplierRequest
import com.puntoclick.data.model.supplier.UpdateSupplierRequest
import com.puntoclick.features.utils.*
import io.ktor.server.plugins.requestvalidation.*

val locale = GlobalLocale.locale
fun CreateSupplierRequest.validateCreateSupplierRequest(): ValidationResult {
    return when {
        !name.validateStringRequest(20) -> ValidationResult.Invalid(locale.getString(StringResourcesKey.VALID_CATEGORY_NAME_ERROR_KEY))
        !company.validateStringRequest(20) -> ValidationResult.Invalid(locale.getString(StringResourcesKey.VALID_CATEGORY_NAME_ERROR_KEY))
        !isValidEmail(email) -> ValidationResult.Invalid(locale.getString(StringResourcesKey.INVALID_EMAIL_ERROR_KEY))
        !isValidCellPhoneNumber(phoneNumber) -> ValidationResult.Invalid(locale.getString(StringResourcesKey.INVALID_PHONE_ERROR_KEY))
        else -> ValidationResult.Valid
    }
}

fun UpdateSupplierRequest.validateUpdateCategoryRequest(): ValidationResult {
    return when {
        !name.validateStringRequest(20) -> ValidationResult.Invalid(locale.getString(StringResourcesKey.VALID_CATEGORY_NAME_ERROR_KEY))
        !company.validateStringRequest(20) -> ValidationResult.Invalid(locale.getString(StringResourcesKey.VALID_CATEGORY_NAME_ERROR_KEY))
        !isValidEmail(email) -> ValidationResult.Invalid(locale.getString(StringResourcesKey.INVALID_EMAIL_ERROR_KEY))
        !isValidCellPhoneNumber(phoneNumber) -> ValidationResult.Invalid(locale.getString(StringResourcesKey.INVALID_PHONE_ERROR_KEY))
        else -> ValidationResult.Valid
    }
}