package com.puntoclick.features.userstoreassignment.route

import com.puntoclick.data.model.UUIDAppRequest
import com.puntoclick.data.model.userstoreassigment.UpdateUserStoreAssignmentRequest
import com.puntoclick.data.model.userstoreassigment.UserStoreAssignmentRequest
import com.puntoclick.data.utils.ROLE_IDENTIFIER
import com.puntoclick.data.utils.TEAM_IDENTIFIER
import com.puntoclick.features.userstoreassignment.controller.UserStoreAssignmentController
import com.puntoclick.features.utils.handleResult
import com.puntoclick.features.utils.retrieveLocale
import com.puntoclick.plugins.getIdentifier
import com.puntoclick.security.AppEncryption
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.core.qualifier.named
import org.koin.ktor.ext.inject

fun Route.userStoreAssignmentRouting() {

    val appEncryption by inject<AppEncryption>(qualifier = named("AES"))
    val userStoreAssignmentController by inject<UserStoreAssignmentController>()

    route("/user-store-assignment") {
        post("/assign") {
            val request = call.receive<UserStoreAssignmentRequest>()
            val locale = call.retrieveLocale()
            val roleId = call.getIdentifier(appEncryption, ROLE_IDENTIFIER)
            val teamId = call.getIdentifier(appEncryption, TEAM_IDENTIFIER)
            val result = userStoreAssignmentController.assignUserToStore(request, locale, roleId, teamId)
            call.respond(message = result.handleResult(), status = result.status)
        }

        get("/by-worker") {
            val locale = call.retrieveLocale()
            val workerId = call.receive<UUIDAppRequest>().id
            val roleId = call.getIdentifier(appEncryption, ROLE_IDENTIFIER)
            val teamId = call.getIdentifier(appEncryption, TEAM_IDENTIFIER)
            val result = userStoreAssignmentController.getUserStoreAssignmentsByWorker(workerId, locale, roleId, teamId)
            call.respond(message = result.handleResult(), status = result.status)
        }

        delete("/remove") {
            val assignmentId = call.receive<UUIDAppRequest>().id
            val locale = call.retrieveLocale()
            val roleId = call.getIdentifier(appEncryption, ROLE_IDENTIFIER)
            val teamId = call.getIdentifier(appEncryption, TEAM_IDENTIFIER)
            val result = userStoreAssignmentController.removeUserFromStore(assignmentId, locale, roleId, teamId)
            call.respond(message = result.handleResult(), status = result.status)
        }

        get("/users-by-store") {
            val storeId = call.receive<UUIDAppRequest>().id
            val locale = call.retrieveLocale()
            val roleId = call.getIdentifier(appEncryption, ROLE_IDENTIFIER)
            val teamId = call.getIdentifier(appEncryption, TEAM_IDENTIFIER)
            val result = userStoreAssignmentController.getUsersByStore(storeId, locale, roleId, teamId)
            call.respond(message = result.handleResult(), status = result.status)
        }

        get("/stores-by-worker") {
            val workerId = call.receive<UUIDAppRequest>().id
            val locale = call.retrieveLocale()
            val roleId = call.getIdentifier(appEncryption, ROLE_IDENTIFIER)
            val teamId = call.getIdentifier(appEncryption, TEAM_IDENTIFIER)
            val result = userStoreAssignmentController.getStoresByWorker(workerId, locale, roleId, teamId)
            call.respond(message = result.handleResult(), status = result.status)
        }

        post("/update") {
            val request = call.receive<UpdateUserStoreAssignmentRequest>()
            val locale = call.retrieveLocale()
            val roleId = call.getIdentifier(appEncryption, ROLE_IDENTIFIER)
            val teamId = call.getIdentifier(appEncryption, TEAM_IDENTIFIER)
            val result = userStoreAssignmentController.updateUserStoreAssignment(request, locale, roleId, teamId)
            call.respond(message = result.handleResult(), status = result.status)
        }

    }
}