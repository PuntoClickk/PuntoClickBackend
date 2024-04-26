package com.puntoclick.features.roles.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateRoleRequest(
    val name: String
)