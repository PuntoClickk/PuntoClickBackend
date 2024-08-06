package com.puntoclick.features.permission

import com.puntoclick.data.model.permission.AddPermissionRequest
import com.puntoclick.data.model.permission.DeletePermissionRequest
import com.puntoclick.data.model.permission.UpdatePermissionRequest
import com.puntoclick.data.utils.TEAM_IDENTIFIER
import com.puntoclick.data.utils.USER_IDENTIFIER
import com.puntoclick.features.permission.controller.PermissionController
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

fun Route.permissionRouting() {

    val permissionController by inject<PermissionController>()
    val appEncryption by inject<AppEncryption>(qualifier = named("AES"))

    route("/permission") {
        post("/add") {
            val request = call.receive<AddPermissionRequest>()
            val userId = call.getIdentifier(appEncryption, USER_IDENTIFIER)
            val teamId = call.getIdentifier(appEncryption, TEAM_IDENTIFIER)
            val locale = call.retrieveLocale()
            val result = permissionController.addPermission(userId, teamId, locale, request)
            call.respond(message = result.handleResult(), status = result.status)
        }
        post("/update") {
            val request = call.receive<UpdatePermissionRequest>()
            val userId = call.getIdentifier(appEncryption, USER_IDENTIFIER)
            val teamId = call.getIdentifier(appEncryption, TEAM_IDENTIFIER)
            val locale = call.retrieveLocale()
            val result = permissionController.updatePermission(userId, teamId, locale, request)
            call.respond(message = result.handleResult(), status = result.status)
        }

        post("/permissions") {
            val teamId = call.getIdentifier(appEncryption, TEAM_IDENTIFIER)
            val result = permissionController.getPermissionByTeam(teamId)
            call.respond(message = result.handleResult(), status = result.status)
        }

        post("/delete") {
            val request = call.receive<DeletePermissionRequest>()
            val userId = call.getIdentifier(appEncryption, USER_IDENTIFIER)
            val locale = call.retrieveLocale()
            val result = permissionController.deletePermission(userId, locale, request)
            call.respond(message = result.handleResult(), status = result.status)
        }

    }

}