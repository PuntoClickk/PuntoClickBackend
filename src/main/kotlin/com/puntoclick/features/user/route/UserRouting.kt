package com.puntoclick.features.user.route

import com.puntoclick.data.database.entity.User
import com.puntoclick.data.model.AppRequest
import com.puntoclick.data.model.UUIDAppRequest
import com.puntoclick.features.user.controller.UserController
import com.puntoclick.features.user.model.CreateUserRequest
import com.puntoclick.features.utils.handleResult
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject


fun Route.userRouting() {

    val userController by inject<UserController>()

    route("/user") {

        post("/add") {
            val request = call.receive<AppRequest<CreateUserRequest>>()
            val result = userController.createUser(request.data)
            call.respond(message = result.handleResult(), status = result.status)
        }

        post("/") {
            val request = call.receive<AppRequest<UUIDAppRequest>>()
            val result = userController.getUser(userId = request.data.id)
            call.respond(message = result.handleResult(), status = result.status)
        }

        post("/all") {
            val request = call.receive<AppRequest<UUIDAppRequest>>()
            val result = userController.allUsers(teamId = request.data.id)
            call.respond(message = result.handleResult(), status = result.status)
        }

        post("/update") {
            val request: AppRequest<User> = call.receive()
            val user = request.data
            val result = userController.updateUser(user)
            call.respond(message = result.handleResult(), status = result.status)
        }

        post("/delete") {
            val request: AppRequest<UUIDAppRequest> = call.receive()
            val result = userController.deleteUser(request.data.id)
            call.respond(message = result.handleResult(), status = result.status)
        }

    }
}
