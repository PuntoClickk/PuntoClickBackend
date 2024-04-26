package com.puntoclick.plugins

import com.puntoclick.data.model.AppRequest
import com.puntoclick.features.user.model.CreateUserRequest
import com.puntoclick.features.user.validation.validateCreateUserRequest
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*


fun Application.configureRequestValidation() {

    install(RequestValidation) {
        validate<AppRequest<CreateUserRequest>> {
            createUserRequest -> createUserRequest.data.validateCreateUserRequest()
        }
    }

}