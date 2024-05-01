package com.puntoclick.features.team.validation

import com.puntoclick.data.model.team.CreateTeamRequest
import com.puntoclick.features.utils.validateStringRequest
import io.ktor.server.plugins.requestvalidation.*

fun CreateTeamRequest.validateTeamRequest (): ValidationResult {
    return when {
        !name.validateStringRequest() -> ValidationResult.Invalid("Invalid name")
        else -> ValidationResult.Valid
    }
}
