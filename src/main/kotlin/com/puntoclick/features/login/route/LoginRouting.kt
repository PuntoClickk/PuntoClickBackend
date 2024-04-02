package com.puntoclick.features.login.route

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.loginRouting(jwtParamsJWT: JWTParams) {

    route("/login") {
        post("/auth") {
            jwtParamsJWT.apply {
                val token = JWT.create()
                    .withAudience(audience) // TODO(Encrypt)
                    .withIssuer(issuer) // TODO(Encrypt)
                    .withClaim("username", "test")// TODO(Encrypt)
                    .withClaim("asdas","asddasd")// TODO(Encrypt)
                    .withExpiresAt(Date(System.currentTimeMillis() + 60000))
                    .sign(Algorithm.HMAC256(secret))
                call.respond(hashMapOf("token" to token))
            }
        }
    }
}

data class JWTParams(val  audience: String, val issuer: String, val secret: String, val realm: String)