package com.puntoclick.features.category.route

import com.puntoclick.data.model.UUIDAppRequest
import com.puntoclick.data.model.category.CreateCategoryRequest
import com.puntoclick.data.model.category.UpdateCategoryRequest
import com.puntoclick.data.utils.ROLE_IDENTIFIER
import com.puntoclick.data.utils.TEAM_IDENTIFIER
import com.puntoclick.data.utils.USER_IDENTIFIER
import com.puntoclick.features.category.controller.CategoryController
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

fun Route.categoryRouting() {

    val categoryController by inject<CategoryController>()
    val appEncryption by inject<AppEncryption> (qualifier = named("AES"))

    route("/category") {

        post("/add") {
            val request = call.receive<CreateCategoryRequest>()
            val user = call.getIdentifier(appEncryption, USER_IDENTIFIER)
            val team = call.getIdentifier(appEncryption, TEAM_IDENTIFIER)
            val role = call.getIdentifier(appEncryption, ROLE_IDENTIFIER)
            val locale = call.retrieveLocale()
            val result = categoryController.addCategory(locale, request, user, role, team)
            call.respond(message = result.handleResult(), status = result.status)
        }

        post("/all") {
            val team = call.getIdentifier(appEncryption, TEAM_IDENTIFIER)
            val role = call.getIdentifier(appEncryption, ROLE_IDENTIFIER)
            val locale = call.retrieveLocale()
            val result = categoryController.allCategories(locale, role, team)
            call.respond(message = result.handleResult(), status = result.status)
        }

        post("/") {
            val request = call.receive<UUIDAppRequest>()
            val locale = call.retrieveLocale()
            val team = call.getIdentifier(appEncryption, TEAM_IDENTIFIER)
            val role = call.getIdentifier(appEncryption, ROLE_IDENTIFIER)
            val result = categoryController.getCategory(locale, request.id, role, team)
            call.respond(message = result.handleResult(), status = result.status)
        }

        post("/update") {
            val request : UpdateCategoryRequest = call.receive()
            val team = call.getIdentifier(appEncryption, TEAM_IDENTIFIER)
            val role = call.getIdentifier(appEncryption, ROLE_IDENTIFIER)
            val locale = call.retrieveLocale()
            val result = categoryController.updateCategory(locale, request, role, team)
            call.respond(message = result.handleResult(), status = result.status)
        }

        post("/delete") {
            val request = call.receive<UUIDAppRequest>()
            val team = call.getIdentifier(appEncryption, TEAM_IDENTIFIER)
            val role = call.getIdentifier(appEncryption, ROLE_IDENTIFIER)
            val locale = call.retrieveLocale()
            val result = categoryController.deleteCategory(locale, request.id, role, team)
            call.respond(message = result.handleResult(), status = result.status)
        }
    }
}