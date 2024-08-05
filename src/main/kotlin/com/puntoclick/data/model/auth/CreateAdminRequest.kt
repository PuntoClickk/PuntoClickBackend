package com.puntoclick.data.model.auth

import com.puntoclick.data.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*


@Serializable
data class CreateAdminRequest(
    val name: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val password: String,
    val birthday: Long,
    val teamName: String,
    val isPending: Boolean = true
)

@Serializable
data class CreateUserRequest(
    val name: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val password: String,
    val birthday: Long,
    val invitationCode: String
)

@Serializable
data class CreateUserData(
    val name: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val password: String,
    val type: Int,
    val birthday: Long,
    @Serializable(with = UUIDSerializer::class)
    val role: UUID,
    @Serializable(with = UUIDSerializer::class)
    val team: UUID,
    val isActive: Boolean,
    val isPending: Boolean
)



fun CreateAdminRequest.mapCreateUserRequestToUser(
    role: UUID,
    team: UUID,
    type: Int
): CreateUserData {
    return CreateUserData(
        name = name,
        lastName = lastName,
        email = email,
        phoneNumber = phoneNumber,
        password = password,
        type = type,
        birthday = birthday,
        role = role,
        team = team,
        isActive = true,
        isPending = isPending
    )
}

fun CreateUserRequest.mapCreateUserRequestToUser(
    role: UUID,
    team: UUID,
    type: Int
): CreateUserData {
    return CreateUserData(
        name = name,
        lastName = lastName,
        email = email,
        phoneNumber = phoneNumber,
        password = password,
        type = type,
        birthday = birthday,
        role = role,
        team = team,
        isActive = false,
        isPending = false
    )

}
