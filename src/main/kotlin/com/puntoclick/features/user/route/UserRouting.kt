package com.puntoclick.features.user.route

import com.puntoclick.data.database.entity.User
import com.puntoclick.data.model.AppRequest
import com.puntoclick.features.user.controller.UserController
import com.puntoclick.features.utils.handleResult
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject


fun Route.userRouting() {

    val userController by inject<UserController>()

    route("/user"){

        post("/add") {
            val request = call.receive<AppRequest<User>>()
            val result = userController.createUSer(request.data)
            call.respond(message = result.handleResult(), status= result.status)
        }

      /*  post("/") {
            val request = call.receive<AppRequest<UUIDAppRequest>>()
            val result = UserController.getUser(request.data.id)
            call.respond(message = result.handleResult(), status= result.status)
        }

        post("/update") {
            val request : AppRequest<User> = call.receive()
            val User = request.data
            val result = UserController.updateUser(User.id, name = User.name)
            call.respond(message = result.handleResult() , status = result.status )
        }

        post("/delete") {
            val request : AppRequest<UUIDAppRequest> = call.receive()
            val result = UserController.deleteUser(request.data.id)
            call.respond(result)
        }*/

    }
}
