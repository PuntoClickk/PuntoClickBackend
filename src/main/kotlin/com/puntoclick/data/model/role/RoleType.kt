package com.puntoclick.data.model.role

import kotlinx.serialization.Serializable

@Serializable
enum class RoleType(val type: Int, val roleName: String) {
    ADMIN(1, "ADMIN"),
    MANAGER(2, "MANAGER"),
    WORKER(3, "WORKER"),
    VIEWER(4, "VIEWER");

    companion object {
        fun fromType(type: Int): RoleType {
            return RoleType.entries.find { it.type == type }
                ?: throw IllegalArgumentException("No RoleType with type: $type")
        }

        fun fromRoleName(roleName: String): RoleType {
            return RoleType.entries.find { it.roleName.equals(roleName, ignoreCase = true) }
                ?: throw IllegalArgumentException("No RoleType with roleName: $roleName")
        }
    }

}