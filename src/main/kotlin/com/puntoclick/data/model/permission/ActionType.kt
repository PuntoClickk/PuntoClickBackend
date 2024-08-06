package com.puntoclick.data.model.permission

enum class ActionType(val actionName: String) {
    WRITE("Write"),
    READ("Read"),
    UPDATE("Update"),
    DELETE("Delete")
}