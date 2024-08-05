package com.puntoclick.features.invitation.validation

import com.puntoclick.data.model.invitation.AcceptInvitationRequest
import io.ktor.server.plugins.requestvalidation.*

fun AcceptInvitationRequest.validateAcceptInvitationRequest(): ValidationResult{
    return when {
        invitationCode.isEmpty() -> ValidationResult.Invalid("Invalid Code")
        else -> ValidationResult.Valid
    }
}