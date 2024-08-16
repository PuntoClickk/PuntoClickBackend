package com.puntoclick.data.model.role

import kotlinx.serialization.Serializable

@Serializable
enum class RoleType(val type: Int, val roleName: String) {
    ADMIN(1, "ADMIN"),
    MANAGER(2, "MANAGER"),
    WORKER(3, "WORKER"),
    VIEWER(4, "VIEWER");
}