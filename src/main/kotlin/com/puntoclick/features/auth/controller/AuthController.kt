package com.puntoclick.features.auth.controller

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.puntoclick.data.database.user.daofacade.UserDaoFacade
import com.puntoclick.data.model.AppResult
import com.puntoclick.data.model.auth.LoginRequest
import com.puntoclick.data.model.user.UserLogin
import com.puntoclick.features.utils.createError
import com.puntoclick.plugins.JWTParams
import com.puntoclick.plugins.loadECPrivateKey
import io.ktor.http.*
import java.util.*

class AuthController(
    private val userDaoFacade: UserDaoFacade
) {

    suspend fun login(loginRequest: LoginRequest, jwtParams: JWTParams): AppResult<String> {
        val user = userDaoFacade.user(loginRequest.email)
        println(user)
        return user?.let {
            if (validatePassword(password = loginRequest.password, userLogin = it))
            AppResult.Success(data = createToken(user.id, jwtParams), appStatus = HttpStatusCode.OK)
            else  createError(title = "Error", "Password wrong")
        } ?: createError(title = "Error", "Password wrong")
    }

    private fun createToken(uuid: UUID,jwtParams: JWTParams): String {
        jwtParams.apply {
            val algorithm = Algorithm.ECDSA256(loadECPrivateKey(jwtParams.private))
            val now = System.currentTimeMillis()
            val text =  uuid.toString()//appEncryption.encrypt("Something")
            val token = JWT.create()
                .withIssuer(issuer)
                .withAudience(audience)
                .withClaim("identifier", text)
                .withExpiresAt(Date(now + (60 * 1000)))
                .sign(algorithm)
            return token
        }
    }


    private fun validatePassword(password: String, userLogin: UserLogin): Boolean {
        return (password == userLogin.password)
    }


}