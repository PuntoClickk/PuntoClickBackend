package com.puntoclick.data.model.role

import java.util.*

data class RoleData(
    val id: UUID,
    val type: Int,
    val name: String,
    val isActive: Boolean
)
