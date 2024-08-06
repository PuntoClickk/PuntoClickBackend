package com.puntoclick.data.model.action

enum class ActionType(val actionName: String) {
    WRITE("WRITE"),
    READ("READ"),
    UPDATE("UPDATE"),
    DELETE("DELETE")
}