package com.puntoclick.data.database.init

import com.puntoclick.data.database.action.table.ActionTable
import com.puntoclick.data.model.GlobalActionIds
import com.puntoclick.data.model.action.ActionType

fun initializeActionsIds() {
    ActionType.entries.forEach { actionType ->
        val actionId = ActionTable
            .select(ActionTable.uuid)
            .where { ActionTable.name eq actionType.actionName }
            .map { it[ActionTable.uuid] }
            .singleOrNull() ?: throw Exception("Action ${actionType.actionName} not found")
        GlobalActionIds.set(actionType, actionId)
    }
}