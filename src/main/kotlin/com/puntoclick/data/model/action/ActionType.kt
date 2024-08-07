package com.puntoclick.data.model.action

import kotlinx.serialization.Serializable

@Serializable
enum class ActionType(val type: Int, val actionName: String) {
    WRITE(1,"WRITE"),
    READ(2,"READ"),
    UPDATE(3,"UPDATE"),
    DELETE(4,"DELETE");

    companion object {

        fun fromType(type: Int): ActionType {
            return entries.find { it.type == type }
                ?: throw IllegalArgumentException("No ActionType with type: $type")
        }

        fun fromActionName(actionName: String): ActionType {
            return entries.find { it.actionName.equals(actionName, ignoreCase = true) }
                ?: throw IllegalArgumentException("No ActionType with actionName: $actionName")
        }
    }

}