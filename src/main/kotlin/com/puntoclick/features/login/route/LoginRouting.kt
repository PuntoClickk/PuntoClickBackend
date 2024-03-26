package com.puntoclick.features.login.route

import com.puntoclick.data.database.entity.User
import com.puntoclick.firebase.FIREBASE_AUTH
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.loginRouting() {

    authenticate(FIREBASE_AUTH) {
        get("/authenticated") {
            val user: User = call.principal() ?: return@get call.respond(HttpStatusCode.Unauthorized)
            call.respond("User is authenticated: $user")
        }
    }

    route("/login") {

    }
}