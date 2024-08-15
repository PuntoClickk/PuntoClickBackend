package com.puntoclick.plugins

import com.puntoclick.data.model.ErrorResponse
import com.puntoclick.features.auth.route.authRouting
import com.puntoclick.features.category.route.categoryRouting
import com.puntoclick.features.invitation.route.invitationRouting
import com.puntoclick.features.permission.permissionRouting
import com.puntoclick.features.roles.route.roleRouting
import com.puntoclick.features.supplier.route.supplierRouting
import com.puntoclick.features.team.route.teamRouting
import com.puntoclick.features.user.route.userRouting
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Application.configureRouting() {

    val jwtParams = getJWTParams()

    install(Resources)
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respond(message = ErrorResponse("Error",cause.message ?: "Error App"), status = HttpStatusCode.BadRequest)
        }
        exception<RequestValidationException> { call, cause ->
            call.respond(message = ErrorResponse("Error",cause.reasons.getOrNull(0) ?: "Error App"), status = HttpStatusCode.BadRequest)
        }
    }
    routing {
        authRouting(jwtParams)
        authenticate("auth-jwt") {
            roleRouting()
            teamRouting()
            userRouting()
            invitationRouting()
            categoryRouting()
            permissionRouting()
            supplierRouting()
        }
    }
}
