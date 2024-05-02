package com.puntoclick.features.auth.route

import com.puntoclick.data.model.auth.LoginRequest
import com.puntoclick.features.auth.controller.AuthController
import com.puntoclick.features.utils.handleResult
import com.puntoclick.plugins.JWTParams
import com.puntoclick.security.AppEncryption
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.authRouting(jwtParams: JWTParams, appEncryption: AppEncryption) {

    val authController by inject<AuthController>()

    route("/auth") {
        post("/login") {
            val loginRequest = call.receive<LoginRequest>()
            val result = authController.login(loginRequest, jwtParams)
            call.respond(message = result.handleResult(), status = result.status)
        }
    }
}
