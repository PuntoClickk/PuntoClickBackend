package com.puntoclick.features.category.route

import com.puntoclick.data.model.UUIDAppRequest
import com.puntoclick.data.model.category.CreateCategoryRequest
import com.puntoclick.data.model.category.UpdateCategoryRequest
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
import java.util.UUID

fun Route.categoryRouting() {

    val categoryController by inject<CategoryController>()
    val appEncryption by inject<AppEncryption> (qualifier = named("AES"))

    route("/category") {

        post("/add") {
            val request = call.receive<CreateCategoryRequest>()
            val user = call.getIdentifier(appEncryption, USER_IDENTIFIER)
            val locale = call.retrieveLocale()
            val result = categoryController.addCategory(request, user, locale)
            call.respond(message = result.handleResult(), status = result.status)
        }

        post("/all") {
            val result = categoryController.allCategories()
            call.respond(message = result.handleResult(), status = result.status)
        }

        post("/") {
            val request = call.receive<UUIDAppRequest>()
            val locale = call.retrieveLocale()
            val result = categoryController.getCategory(request.id, locale)
            call.respond(message = result.handleResult(), status = result.status)
        }

        post("/update") {
            val request : UpdateCategoryRequest = call.receive()
            val locale = call.retrieveLocale()
            val result = categoryController.updateCategory(request, locale)
            call.respond(message = result.handleResult(), status = result.status)
        }

        post("/delete") {
            val request = call.receive<UUIDAppRequest>()
            val locale = call.retrieveLocale()
            val result = categoryController.deleteCategory(request.id, locale)
            call.respond(message = result.handleResult(), status = result.status)
        }
    }
}