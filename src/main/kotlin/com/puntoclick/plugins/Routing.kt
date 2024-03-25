package com.puntoclick.plugins

import com.puntoclick.di.Controllers
import com.puntoclick.features.login.route.ParamsJWT
import com.puntoclick.features.login.route.loginRouting
import com.puntoclick.features.roles.route.roleRouting
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

import java.io.File


fun Application.configureRouting(controllers: Controllers, jwt: ParamsJWT) {

    install(Resources)
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
    routing {
        loginRouting(jwt)
        authenticate("auth-jwt") {
            roleRouting(controllers.roleController)
        }


     /*   staticFiles(".well-known", dir = ) {
            staticRootFolder = File("certs")
            staticFiles("jwks.json")
        }

        staticFiles(".well-known", File("jwks")){
            extensions("json")
        }*/
    }
}
