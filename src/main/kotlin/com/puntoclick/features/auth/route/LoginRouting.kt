package com.puntoclick.features.auth.route

import com.puntoclick.data.model.auth.LoginRequest
import com.puntoclick.data.model.auth.CreateAdminRequest
import com.puntoclick.features.auth.controller.AuthController
import com.puntoclick.features.utils.handleResult
import com.puntoclick.plugins.JWTParams
import com.puntoclick.features.utils.retrieveLocale
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.authRouting(jwtParams: JWTParams) {

    val authController by inject<AuthController>()


    route("/auth") {

        post("/add") {
            val request = call.receive<CreateAdminRequest>()
            val locale = call.retrieveLocale()
            val result = authController.createAdmin(request, locale)
            call.respond(message = result.handleResult(), status = result.status)
        }

        post("/login") {
            val loginRequest = call.receive<LoginRequest>()
            val locale = call.retrieveLocale()
            val result = authController.login(loginRequest, jwtParams, locale)
            call.respond(message = result.handleResult(), status = result.status)
        }
    }
}
