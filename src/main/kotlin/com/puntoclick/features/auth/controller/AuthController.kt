package com.puntoclick.features.auth.controller

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.puntoclick.data.database.user.daofacade.UserDaoFacade
import com.puntoclick.data.model.AppResult
import com.puntoclick.data.model.auth.LoginRequest
import com.puntoclick.data.model.auth.TokenResponse
import com.puntoclick.data.model.user.CreateUserRequest
import com.puntoclick.features.utils.createError
import com.puntoclick.plugins.JWTParams
import com.puntoclick.plugins.loadECPrivateKey
import com.puntoclick.security.AppEncryption
import com.puntoclick.security.verifyPassword
import io.ktor.http.*
import java.util.*

class AuthController(
    private val userDaoFacade: UserDaoFacade,
    private val appEncryption: AppEncryption
) {

    suspend fun createUser(createUserRequest: CreateUserRequest): AppResult<Boolean> {
        return if (userDaoFacade.addUser(createUserRequest)) AppResult.Success(
            data = true, appStatus = HttpStatusCode.OK
        )
        else createError(title = "Error", "User not created", HttpStatusCode.BadRequest)
    }

    suspend fun login(loginRequest: LoginRequest, jwtParams: JWTParams): AppResult<TokenResponse> {
        val user = userDaoFacade.user(loginRequest.email)
        return user?.let {
            if (validatePassword(password = loginRequest.password, it.password))
                AppResult.Success(data = createToken(user.id, jwtParams), appStatus = HttpStatusCode.OK)
            else  createError(title = "Error", "Password wrong1")
        } ?: createError(title = "Error", "Password wrong2")
    }

    private fun createToken(uuid: UUID,jwtParams: JWTParams): TokenResponse {
        jwtParams.apply {
            val algorithm = Algorithm.ECDSA256(loadECPrivateKey(jwtParams.private))
            val now = System.currentTimeMillis()
            val text =  appEncryption.encrypt(uuid.toString())
            val token = JWT.create()
                .withIssuer(issuer)
                .withAudience(audience)
                .withClaim("identifier", text)
                .withExpiresAt(Date(now + (60 * 1000)))
                .sign(algorithm)
            return TokenResponse(token)
        }
    }


    private fun validatePassword(password: String,hashedPassword: String ): Boolean {
        return password.verifyPassword(hashedPassword = hashedPassword)
    }


}