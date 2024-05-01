package com.puntoclick.features.roles.route

import com.puntoclick.data.model.UUIDAppRequest
import com.puntoclick.features.roles.controller.RoleController
import com.puntoclick.features.roles.model.CreateRoleRequest
import com.puntoclick.features.roles.model.UpdateRoleRequest
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
            val request = call.receive<CreateRoleRequest>()
            val result = roleController.addRole(request)
            call.respond(message = result.handleResult(), status = result.status)
        }

        post("/all") {
            val result = roleController.getRoles()
            call.respond(message = result.handleResult() , status = result.status )
        }

        post("/") {
            val request = call.receive<UUIDAppRequest>()
            val result = roleController.getRole(request.id)
            call.respond(message = result.handleResult(), status= result.status)
        }

        post("/update") {
            val role : UpdateRoleRequest = call.receive()
            val result = roleController.updateRole(role)
            call.respond(message = result.handleResult() , status = result.status )
        }

        post("/delete") {
            val uuidAppRequest : UUIDAppRequest = call.receive()
            val result = roleController.deleteRole(uuidAppRequest.id)
            call.respond(message = result.handleResult() , status = result.status)
        }
    }
}
