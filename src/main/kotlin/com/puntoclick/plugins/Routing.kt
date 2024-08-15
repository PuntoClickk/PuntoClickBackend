package com.puntoclick.plugins

import com.puntoclick.AppEnvironment
import com.puntoclick.data.model.ErrorResponse
import com.puntoclick.features.auth.route.authRouting
import com.puntoclick.features.category.route.categoryRouting
import com.puntoclick.features.invitation.route.invitationRouting
import com.puntoclick.features.permission.route.permissionRouting
import com.puntoclick.features.roles.route.roleRouting
import com.puntoclick.features.supplier.route.supplierRouting
import com.puntoclick.features.store.routing.storeRouting
import com.puntoclick.features.team.route.teamRouting
import com.puntoclick.features.user.route.userRouting
import com.puntoclick.features.utils.*
import com.puntoclick.getEnvironment
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.ratelimit.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Application.configureRouting() {

    val env = getEnvironment()
    val jwtParams = getJWTParams()

    install(Resources)
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            val locale = call.retrieveLocale()
            if (env == AppEnvironment.DEV)
                call.respond(
                    message = ErrorResponse("Error", cause.message ?: "Error App"),
                    status = HttpStatusCode.BadRequest
                )
            else locale.createError()
        }
        exception<RequestValidationException> { call, cause ->
            val locale = call.retrieveLocale()
            if (env == AppEnvironment.DEV)
                call.respond(
                    message = ErrorResponse("Error", cause.reasons.getOrNull(0) ?: "Error App"),
                    status = HttpStatusCode.BadRequest
                )
            else locale.createError()
        }

        status(HttpStatusCode.TooManyRequests) { call, status ->
            val locale = call.retrieveLocale()
            val retryAfter = call.response.headers["Retry-After"]

            if (env == AppEnvironment.DEV)
                call.respond(
                    message = ErrorResponse("Error", "429: Too many requests. Wait for $retryAfter seconds."),
                    status = HttpStatusCode.BadRequest
                )
            else call.respond(
                locale.createError(
                    descriptionKey = StringResourcesKey.TOO_MANY_REQUESTS_ERROR_KEY,
                    status = status
                ).handleResult()
            )
        }
    }

    routing {
        rateLimit(RateLimitName(LimitType.PUBLIC.value)) { authRouting(jwtParams) }
        authenticate("auth-jwt") {
            rateLimit(RateLimitName(LimitType.PROTECTED.value)) {
                roleRouting()
                teamRouting()
                userRouting()
                invitationRouting()
                categoryRouting()
                permissionRouting()
                supplierRouting()
                storeRouting()
            }
        }
    }

}
