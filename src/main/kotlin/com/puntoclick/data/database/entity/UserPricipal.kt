package com.puntoclick.data.database.entity

import io.ktor.server.auth.*

data class User(val userId: String = "", val displayName: String = "") : Principal
