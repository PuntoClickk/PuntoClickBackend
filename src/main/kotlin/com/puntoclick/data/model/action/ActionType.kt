package com.puntoclick.data.model.action

import kotlinx.serialization.Serializable

@Serializable
enum class ActionType(val type: Int, val actionName: String) {
    WRITE(1,"WRITE"),
    READ(2,"READ"),
    UPDATE(3,"UPDATE"),
    DELETE(4,"DELETE");
}