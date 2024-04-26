package com.puntoclick.features.roles.route

import com.puntoclick.data.database.entity.Role
import com.puntoclick.data.model.AppRequest
import com.puntoclick.data.model.UUIDAppRequest
import com.puntoclick.features.roles.controller.RoleController
import com.puntoclick.features.utils.handleResult
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.roleRouting() {

    val roleController by inject<RoleController>()

    route("/role"){

        post("/add") {
            val request = call.receive<AppRequest<String>>()
            val result = roleController.addRole(request.data)
            call.respond(message = result.handleResult(), status = result.status)
        }

        post("/all") {
            val result = roleController.getRoles()
            call.respond(message = result.handleResult() , status = result.status )
        }

        post("/") {
            val request = call.receive<AppRequest<UUIDAppRequest>>()
            val result = roleController.getRole(request.data.id)
            call.respond(message = result.handleResult(), status= result.status)
        }

        post("/update") {
            val request : AppRequest<Role> = call.receive()
            val role = request.data
            val result = roleController.updateRole(role.id, role.name)
            call.respond(message = result.handleResult() , status = result.status )
        }

        post("/delete") {
            val request : AppRequest<UUIDAppRequest> = call.receive()
            val result = roleController.deleteRole(request.data.id)
            call.respond(message = result.handleResult() , status = result.status)
        }
    }
}
