package com.puntoclick.plugins

import com.puntoclick.features.roles.database.RoleDaoFacade
import com.puntoclick.features.roles.route.roleRouting
import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.resources.*
import io.ktor.server.resources.Resources
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

fun Application.configureRouting(dao: RoleDaoFacade) {
    install(Resources)
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause" , status = HttpStatusCode.InternalServerError)
        }
    }
    routing {
        roleRouting(dao)
    }
}

@Serializable
@Resource("/articles")
class Articles(val sort: String? = "new")
