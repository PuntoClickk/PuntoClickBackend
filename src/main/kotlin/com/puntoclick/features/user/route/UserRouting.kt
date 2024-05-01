package com.puntoclick.features.user.route

import com.puntoclick.data.database.entity.User
import com.puntoclick.data.model.UUIDAppRequest
import com.puntoclick.features.user.controller.UserController
import com.puntoclick.data.model.user.CreateUserRequest
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
            val request = call.receive<CreateUserRequest>()
            val result = userController.createUser(request)
            call.respond(message = result.handleResult(), status = result.status)
        }

        post("/") {
            val request = call.receive<UUIDAppRequest>()
            val result = userController.getUser(userId = request.id)
            call.respond(message = result.handleResult(), status = result.status)
        }

        post("/all") {
            val request = call.receive<UUIDAppRequest>()
            val result = userController.allUsers(teamId = request.id)
            call.respond(message = result.handleResult(), status = result.status)
        }

        post("/update") {
            val user: User = call.receive()
            val result = userController.updateUser(user)
            call.respond(message = result.handleResult(), status = result.status)
        }

        post("/delete") {
            val request: UUIDAppRequest = call.receive()
            val result = userController.deleteUser(request.id)
            call.respond(message = result.handleResult(), status = result.status)
        }

    }
}
