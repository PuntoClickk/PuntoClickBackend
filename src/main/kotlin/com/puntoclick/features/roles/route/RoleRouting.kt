package com.puntoclick.features.roles.route


import com.puntoclick.data.model.handleResult
import com.puntoclick.features.roles.controller.RoleController
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.roleRouting(roleController: RoleController) {

    route("/role"){

        post("/add") {
            val result = roleController.addRole("test")
            call.respond(result)
        }

        get("/all") {
            val result = roleController.getRoles()
            call.respond(message = result.handleResult() , status = result.status )
        }
    }
}
