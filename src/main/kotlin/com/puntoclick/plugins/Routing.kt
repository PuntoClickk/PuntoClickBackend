package com.puntoclick.plugins

import com.puntoclick.di.Controllers
import com.puntoclick.features.login.route.loginRouting
import com.puntoclick.features.roles.route.roleRouting
import com.puntoclick.firebase.FIREBASE_AUTH
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Application.configureRouting(controllers: Controllers) {

    install(Resources)
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause" , status = HttpStatusCode.InternalServerError)
        }
    }
    routing {
        loginRouting()
        authenticate(FIREBASE_AUTH) {
            roleRouting(controllers.roleController)
        }
    }
}
