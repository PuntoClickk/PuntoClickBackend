package com.puntoclick.plugins

import com.puntoclick.features.roles.model.CreateRoleRequest
import com.puntoclick.features.roles.validation.validateRoleRequest
import com.puntoclick.features.team.model.CreateTeamRequest
import com.puntoclick.features.team.validation.validateTeamRequest
import com.puntoclick.features.user.model.CreateUserRequest
import com.puntoclick.features.user.validation.validateCreateUserRequest
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*


fun Application.configureRequestValidation() {

    install(RequestValidation) {
        validate<CreateUserRequest> {
            createUserRequest -> createUserRequest.validateCreateUserRequest()
        }
        validate<CreateTeamRequest> {
            createTeamRequest -> createTeamRequest.validateTeamRequest()
        }
        validate<CreateRoleRequest> {
            createRoleRequest -> createRoleRequest.validateRoleRequest()
        }
    }

}