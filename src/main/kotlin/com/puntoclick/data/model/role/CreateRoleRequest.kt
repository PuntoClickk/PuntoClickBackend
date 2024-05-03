package com.puntoclick.data.model.role

import kotlinx.serialization.Serializable

@Serializable
data class CreateRoleRequest(
    val type: Int,
    val name: String
)