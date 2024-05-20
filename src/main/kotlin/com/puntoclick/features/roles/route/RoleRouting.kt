package com.puntoclick.features.roles.route

import com.puntoclick.data.model.UUIDAppRequest
import com.puntoclick.data.model.role.CreateRoleRequest
import com.puntoclick.data.model.role.UpdateRoleRequest
import com.puntoclick.data.utils.ROLE_IDENTIFIER
import com.puntoclick.data.utils.TEAM_IDENTIFIER
import com.puntoclick.data.utils.USER_IDENTIFIER
import com.puntoclick.features.roles.controller.RoleController
import com.puntoclick.features.utils.handleResult
import com.puntoclick.plugins.getIdentifier
import com.puntoclick.security.AppEncryption
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.core.qualifier.named
import org.koin.ktor.ext.inject

fun Route.roleRouting() {

    val roleController by inject<RoleController>()
    // TODO remove this, it's just a test
    val appEncryption by inject<AppEncryption> (qualifier = named("AES"))

    route("/role"){

        post("/add") {
            val request = call.receive<CreateRoleRequest>()
            val result = roleController.addRole(request)
            call.respond(message = result.handleResult(), status = result.status)
        }

        post("/all") {
            val result = roleController.getRoles()
            // TODO remove this, it's just a test
            println(call.getIdentifier(appEncryption, USER_IDENTIFIER))
            println(call.getIdentifier(appEncryption, TEAM_IDENTIFIER))
            println(call.getIdentifier(appEncryption, ROLE_IDENTIFIER))
            call.respond(message = result.handleResult(), status = result.status)
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
