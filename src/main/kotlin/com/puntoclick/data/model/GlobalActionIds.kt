package com.puntoclick.data.model

import com.puntoclick.data.model.action.ActionType
import java.util.*

object GlobalActionIds {
    private val actionIdMap: MutableMap<ActionType, UUID> = mutableMapOf()

    fun get(actionType: ActionType): UUID {
        return actionIdMap[actionType] ?: throw Exception("Action ID not found for type: $actionType")
    }

    fun set(actionType: ActionType, id: UUID) {
        actionIdMap[actionType] = id
    }
}
