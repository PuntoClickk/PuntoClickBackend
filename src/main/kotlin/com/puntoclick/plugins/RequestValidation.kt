package com.puntoclick.plugins

import com.puntoclick.data.model.role.CreateRoleRequest
import com.puntoclick.features.roles.validation.validateRoleRequest
import com.puntoclick.data.model.team.CreateTeamRequest
import com.puntoclick.features.team.validation.validateTeamRequest
import com.puntoclick.data.model.auth.CreateAdminRequest
import com.puntoclick.data.model.auth.CreateUserRequest
import com.puntoclick.data.model.auth.LoginRequest
import com.puntoclick.data.model.auth.ValidateEmailRequest
import com.puntoclick.data.model.invitation.AcceptInvitationRequest
import com.puntoclick.features.auth.validation.validateEmailRequest
import com.puntoclick.features.auth.validation.validateLoginRequest
import com.puntoclick.features.invitation.validation.validateAcceptInvitationRequest
import com.puntoclick.features.user.validation.validateCreateUserRequest
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*


fun Application.configureRequestValidation() {

    install(RequestValidation) {
        validate<CreateAdminRequest> { createUserRequest ->
            createUserRequest.validateCreateUserRequest()
        }

        validate<CreateTeamRequest> { createTeamRequest ->
            createTeamRequest.validateTeamRequest()
        }

        validate<CreateRoleRequest> { createRoleRequest ->
            createRoleRequest.validateRoleRequest()
        }

        validate<CreateUserRequest> { createAdminRequest ->
            createAdminRequest.validateCreateUserRequest()
        }

        validate<LoginRequest> { loginRequest ->
            loginRequest.validateLoginRequest()
        }

        validate<ValidateEmailRequest> { validateEmailRequest ->
            validateEmailRequest.validateEmailRequest()
        }

        validate<AcceptInvitationRequest> { acceptInvitationRequest ->
            acceptInvitationRequest.validateAcceptInvitationRequest()
        }
    }
}
