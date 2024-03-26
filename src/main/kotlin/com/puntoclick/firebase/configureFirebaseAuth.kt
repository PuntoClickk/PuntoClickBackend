package com.puntoclick.firebase

import com.puntoclick.data.database.entity.User
import com.puntoclick.plugins.firebase
import io.ktor.server.application.*
import io.ktor.server.auth.*

fun Application.configureFirebaseAuth() {
    install(Authentication) {
        firebase {
            validate {
                // TODO look up user profile from DB
                User(it.uid, it.name.orEmpty())
            }
        }
    }
}