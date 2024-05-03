package com.puntoclick.data.model.user

import com.puntoclick.data.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*


@Serializable
data class CreateUserRequest(
    val name: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val password: String,
    val type: Int,
    val birthday: Long,
    val teamName: String
)

@Serializable
data class CreateUser(
    val name: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val password: String,
    val type: Int,
    val birthday: Long,
    val teamName: String,
    @Serializable(with = UUIDSerializer::class)
    val role: UUID,
    @Serializable(with = UUIDSerializer::class)
    val team: UUID,
)

fun CreateUserRequest.mapCreateUserRequestToUser2(
    role: UUID,
    team: UUID
): CreateUser {
    return CreateUser(
        name = name,
        lastName = lastName,
        email = email,
        phoneNumber = phoneNumber,
        password = password,
        type = type,
        birthday = birthday,
        teamName = teamName,
        role = role,
        team = team
    )
}
