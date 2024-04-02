package com.puntoclick.features.roles.route


import com.puntoclick.data.model.handleResult
import com.puntoclick.features.roles.controller.RoleController
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.roleRouting() {

    val roleController by inject<RoleController>()

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
