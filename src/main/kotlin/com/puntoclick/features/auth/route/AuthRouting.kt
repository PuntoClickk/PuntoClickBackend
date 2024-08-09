package com.puntoclick.features.auth.route

import com.puntoclick.data.model.auth.CreateAdminRequest
import com.puntoclick.data.model.auth.CreateUserRequest
import com.puntoclick.data.model.auth.LoginRequest
import com.puntoclick.data.model.auth.ValidateEmailRequest
import com.puntoclick.data.model.invitation.AcceptInvitationRequest
import com.puntoclick.features.auth.controller.AuthController
import com.puntoclick.features.utils.handleResult
import com.puntoclick.plugins.JWTParams
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.authRouting(jwtParams: JWTParams) {

    val authController by inject<AuthController>()

    route("/auth") {
        post("/create/admin") {
            val request = call.receive<CreateAdminRequest>()
            val result = authController.createAdmin(request)
            call.respond(message = result.handleResult(), status = result.status)
        }

        post("/validate/email") {
            val request = call.receive<ValidateEmailRequest>()
            val result = authController.validateEmail(request)
            call.respond(message = result.handleResult(), status = result.status)
        }

        post("/create/user") {
            val request = call.receive<CreateUserRequest>()
            val result = authController.createUser(request)
            call.respond(message = result.handleResult(), status = result.status)
        }

        post("/login") {
            val loginRequest = call.receive<LoginRequest>()
            val result = authController.login(loginRequest, jwtParams)
            call.respond(message = result.handleResult(), status = result.status)
        }

        post("/accept") {
            val request = call.receive<AcceptInvitationRequest>()
            val result = authController.authenticateToAcceptInvitation(request)
            call.respond(message = result.handleResult(), status = result.status)
        }

    }
}
