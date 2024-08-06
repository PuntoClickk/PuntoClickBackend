package com.puntoclick.data.model.role

enum class RoleType(val type: Int, val roleName: String) {
    ADMIN(1, "Admin"),
    MANAGER(2, "Manager"),
    WORKER(3, "Worker"),
    VIEWER(4, "Viewer")
}